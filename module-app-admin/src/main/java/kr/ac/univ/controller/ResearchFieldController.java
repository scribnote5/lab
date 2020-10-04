package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.researchField.dto.ResearchFieldDto;
import kr.ac.univ.researchField.service.ResearchFieldService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/research-field")
public class ResearchFieldController {
    private final ResearchFieldService researchFieldService;

    public ResearchFieldController(ResearchFieldService researchFieldService) {
        this.researchFieldService = researchFieldService;
    }

    // List
    @GetMapping("/list")
    public String researchFieldList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("researchFieldDtoList", researchFieldService.findResearchFieldList(pageable, searchDto));

        return "/researchField/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String researchFieldForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        ResearchFieldDto researchFieldDto = researchFieldService.findResearchFieldByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (researchFieldDto.isAccess()) {
            model.addAttribute("researchFieldDto", researchFieldDto);

            returnPage = "/researchField/form";
        } else {
            returnPage = "/user/permission-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String researchFieldRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        ResearchFieldDto researchFieldDto = researchFieldService.findResearchFieldByIdx(idx);

        model.addAttribute("researchFieldDto", researchFieldDto);

        return "/researchField/read";
    }
}