package kr.ac.univ.controller;

import kr.ac.univ.aboutUs.dto.AboutUsDto;
import kr.ac.univ.aboutUs.service.AboutUsService;
import kr.ac.univ.common.dto.SearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/about-us")
public class AboutUsController {
    private final AboutUsService aboutUsService;

    public AboutUsController(AboutUsService aboutUsService) {
        this.aboutUsService = aboutUsService;
    }

    // List
    @GetMapping("/list")
    public String aboutUsList(Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("aboutUsDtoList", aboutUsService.findAboutUsList(pageable, searchDto));

        return "aboutUs/list";
    }

    // Form
    @GetMapping("/form{idx}")
    public String aboutUsForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        AboutUsDto aboutUsDto = aboutUsService.findAboutUsByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (aboutUsDto.isAccess()) {
            model.addAttribute("aboutUsDto", aboutUsDto);

            returnPage = "aboutUs/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String aboutUsRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        AboutUsDto aboutUsDto = null;

        aboutUsDto = aboutUsService.findAboutUsByIdx(idx);

        model.addAttribute("aboutUsDto", aboutUsDto);

        return "aboutUs/read";
    }
}