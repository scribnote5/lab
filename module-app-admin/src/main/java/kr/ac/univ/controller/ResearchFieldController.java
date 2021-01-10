package kr.ac.univ.controller;

import kr.ac.univ.category.service.CategoryService;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.researchField.dto.ResearchFieldDto;
import kr.ac.univ.researchField.service.ResearchFieldAttachedFileService;
import kr.ac.univ.researchField.service.ResearchFieldService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/research-field")
public class ResearchFieldController {
    private final ResearchFieldService researchFieldService;
    private final ResearchFieldAttachedFileService researchFieldAttachedFileService;
    private final CategoryService categoryService;

    public ResearchFieldController(ResearchFieldService researchFieldService, ResearchFieldAttachedFileService researchFieldAttachedFileService, CategoryService categoryService) {
        this.researchFieldService = researchFieldService;
        this.researchFieldAttachedFileService = researchFieldAttachedFileService;
        this.categoryService = categoryService;
    }

    // List
    @GetMapping("/list")
    public String researchFieldList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("researchFieldDtoList", researchFieldService.findResearchFieldList(pageable, searchDto));

        return "researchField/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String researchFieldForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        ResearchFieldDto researchFieldDto = researchFieldService.findResearchFieldByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (researchFieldDto.isAccess()) {
            researchFieldDto = researchFieldAttachedFileService.findAttachedFileByResearchFieldIdx(idx, researchFieldDto);

            model.addAttribute("researchFieldDto", researchFieldDto);
            model.addAttribute("categoryDto", categoryService.findCategoryByIdxInActive(researchFieldDto.getCategoryIdx()));
            model.addAttribute("categoryDtoList", categoryService.findCategoryListByActiveStatusIs());

            returnPage = "researchField/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String researchFieldRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        ResearchFieldDto researchFieldDto = null;

        researchFieldDto = researchFieldService.findResearchFieldByIdx(idx);
        researchFieldDto = researchFieldAttachedFileService.findAttachedFileByResearchFieldIdx(idx, researchFieldDto);

        model.addAttribute("researchFieldDto", researchFieldDto);
        model.addAttribute("categoryDto", categoryService.findCategoryByIdxInActive(researchFieldDto.getCategoryIdx()));

        return "researchField/read";
    }
}