package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.introduction.dto.IntroductionDto;
import kr.ac.univ.introduction.service.IntroductionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/introduction")
public class IntroductionController {
    private final IntroductionService introductionService;

    public IntroductionController(IntroductionService introductionService) {
        this.introductionService = introductionService;
    }

    // List
    @GetMapping("/list")
    public String introductionList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("introductionDtoList", introductionService.findIntroductionList(pageable, searchDto));

        return "/introduction/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String introductionForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        IntroductionDto introductionDto = introductionService.findIntroductionByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (introductionDto.isAccess()) {
            model.addAttribute("introductionDto", introductionDto);

            returnPage = "/introduction/form";
        } else {
            returnPage = "/user/permission-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String introductionRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        IntroductionDto introductionDto = introductionService.findIntroductionByIdx(idx);

        model.addAttribute("introductionDto", introductionDto);

        return "/introduction/read";
    }
}