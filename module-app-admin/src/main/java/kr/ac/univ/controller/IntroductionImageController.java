package kr.ac.univ.controller;

import kr.ac.univ.introductionImage.dto.IntroductionImageDto;
import kr.ac.univ.introductionImage.service.IntroductionImageAttachedFileService;
import kr.ac.univ.introductionImage.service.IntroductionImageService;
import kr.ac.univ.common.dto.SearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/introduction-image")
public class IntroductionImageController {
    private final IntroductionImageService introductionImageService;
    private final IntroductionImageAttachedFileService introductionImageAttachedFileService;

    public IntroductionImageController(IntroductionImageService introductionImageService, IntroductionImageAttachedFileService introductionImageAttachedFileService) {
        this.introductionImageService = introductionImageService;
        this.introductionImageAttachedFileService = introductionImageAttachedFileService;
    }

    // List
    @GetMapping("/list")
    public String introductionImageList(Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("introductionImageDtoList", introductionImageService.findIntroductionImageList(pageable, searchDto));

        return "introductionImage/list";
    }

    // Form
    @GetMapping("/form{idx}")
    public String introductionImageForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        IntroductionImageDto introductionImageDto = introductionImageService.findIntroductionImageByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (introductionImageDto.isAccess()) {
            introductionImageDto = introductionImageAttachedFileService.findAttachedFileByIntroductionImageIdx(idx, introductionImageDto);

            model.addAttribute("introductionImageDto", introductionImageDto);
            model.addAttribute("introductionImageDtoList", introductionImageService.findIntroductionImageListByActiveStatusIs());

            returnPage = "introductionImage/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String introductionImageRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        IntroductionImageDto introductionImageDto = null;

        introductionImageDto = introductionImageService.findIntroductionImageByIdx(idx);
        introductionImageDto = introductionImageAttachedFileService.findAttachedFileByIntroductionImageIdx(idx, introductionImageDto);

        model.addAttribute("introductionImageDto", introductionImageDto);

        return "introductionImage/read";
    }
}