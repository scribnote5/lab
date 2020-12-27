package kr.ac.univ.controller;

import kr.ac.univ.category.service.CategoryService;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.seminar.dto.SeminarDto;
import kr.ac.univ.seminar.service.SeminarAttachedFileService;
import kr.ac.univ.seminar.service.SeminarService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/seminar")
public class SeminarController {
    private final SeminarService seminarService;
    private final SeminarAttachedFileService seminarAttachedFileService;
    private final CategoryService categoryService;

    public SeminarController(SeminarService seminarService, SeminarAttachedFileService seminarAttachedFileService, CategoryService categoryService) {
        this.seminarService = seminarService;
        this.seminarAttachedFileService = seminarAttachedFileService;
        this.categoryService = categoryService;
    }

    // List
    @GetMapping("/list")
    public String seminarList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("seminarDtoList", seminarService.findSeminarList(pageable, searchDto));

        return "seminar/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String seminarForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        SeminarDto seminarDto = seminarService.findSeminarByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (seminarDto.isAccess()) {
            seminarDto = seminarAttachedFileService.findAttachedFileBySeminarIdx(idx, seminarDto);

            model.addAttribute("seminarDto", seminarDto);
            model.addAttribute("categoryDto", categoryService.findCategoryByIdxInActive(seminarDto.getCategoryIdx()));
            model.addAttribute("categoryDtoList", categoryService.findCategoryListByActiveStatusIs());

            returnPage = "seminar/form";
        } else {
            returnPage = "user/permission-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String seminarRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        SeminarDto seminarDto = null;

        seminarDto = seminarService.findSeminarByIdx(idx);
        seminarDto = seminarAttachedFileService.findAttachedFileBySeminarIdx(idx, seminarDto);

        model.addAttribute("seminarDto", seminarDto);
        model.addAttribute("categoryDto", categoryService.findCategoryByIdxInActive(seminarDto.getCategoryIdx()));

        return "seminar/read";
    }
}