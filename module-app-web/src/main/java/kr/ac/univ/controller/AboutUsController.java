package kr.ac.univ.controller;

import kr.ac.univ.aboutUs.service.AboutUsService;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.learnMoreRead.dto.LearnMoreReadDto;
import kr.ac.univ.learnMoreRead.service.LearnMoreReadAttachedFileService;
import kr.ac.univ.learnMoreRead.service.LearnMoreReadService;
import kr.ac.univ.learnMoreVideo.dto.LearnMoreVideoDto;
import kr.ac.univ.learnMoreVideo.service.LearnMoreVideoAttachedFileService;
import kr.ac.univ.learnMoreVideo.service.LearnMoreVideoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/about-us")
public class AboutUsController {
    private final AboutUsService aboutUsService;
    private final LearnMoreReadService learnMoreReadService;
    private final LearnMoreReadAttachedFileService learnMoreReadAttachedFileService;
    private final LearnMoreVideoService learnMoreVideoService;
    private final LearnMoreVideoAttachedFileService learnMoreVideoAttachedFileService;

    public AboutUsController(AboutUsService aboutUsService,
                             LearnMoreReadService learnMoreReadService, LearnMoreReadAttachedFileService learnMoreReadAttachedFileService,
                             LearnMoreVideoService learnMoreVideoService, LearnMoreVideoAttachedFileService learnMoreVideoAttachedFileService) {
        this.aboutUsService = aboutUsService;
        this.learnMoreReadService = learnMoreReadService;
        this.learnMoreReadAttachedFileService = learnMoreReadAttachedFileService;
        this.learnMoreVideoService = learnMoreVideoService;
        this.learnMoreVideoAttachedFileService = learnMoreVideoAttachedFileService;
    }

    // Read
    @GetMapping("/")
    public String aboutUsList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        List<LearnMoreReadDto> learnMoreReadDtoList = learnMoreReadService.findAllByActiveStatusIs();
        for (LearnMoreReadDto learnMoreReadDto : learnMoreReadDtoList) {
            learnMoreReadAttachedFileService.findAttachedFileByLearnMoreIdx(learnMoreReadDto.getIdx(), learnMoreReadDto);
        }

        List<LearnMoreVideoDto> learnMoreVideoDtoList = learnMoreVideoService.findAllByActiveStatusIs();
        for (LearnMoreVideoDto learnMoreVideoDto : learnMoreVideoDtoList) {
            learnMoreVideoAttachedFileService.findAttachedFileByLearnMoreIdx(learnMoreVideoDto.getIdx(), learnMoreVideoDto);
        }

        model.addAttribute("aboutUsDto", aboutUsService.findAboutUsByActiveStatusIs());
        model.addAttribute("learnMoreReadDtoList", learnMoreReadDtoList);
        model.addAttribute("learnMoreVideoDtoList", learnMoreVideoDtoList);

        return "aboutUs/read";
    }
}