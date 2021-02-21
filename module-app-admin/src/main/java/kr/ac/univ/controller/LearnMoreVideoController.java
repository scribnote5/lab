package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.exception.BusinessException;
import kr.ac.univ.learnMoreVideo.dto.LearnMoreVideoDto;
import kr.ac.univ.learnMoreVideo.service.LearnMoreVideoAttachedFileService;
import kr.ac.univ.learnMoreVideo.service.LearnMoreVideoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/learn-more-video")
public class LearnMoreVideoController {
    private final LearnMoreVideoService learnMoreVideoService;
    private final LearnMoreVideoAttachedFileService learnMoreVideoAttachedFileService;

    public LearnMoreVideoController(LearnMoreVideoService learnMoreVideoService, LearnMoreVideoAttachedFileService learnMoreVideoAttachedFileService) {
        this.learnMoreVideoService = learnMoreVideoService;
        this.learnMoreVideoAttachedFileService = learnMoreVideoAttachedFileService;
    }

    // List
    @GetMapping("/list")
    public String learnMoreVideoList(Pageable pageable, SearchDto searchDto, Model model) {
        if ("DOWNLOAD_FILE_TYPE".equals(searchDto.getSearchType()) && !("READ".equals(searchDto.getKeyword()) || "VIDEO".equals(searchDto.getKeyword()))) {
            throw new BusinessException("Please Check keyword 'READ' or 'VIDEO'.");
        }

        model.addAttribute("learnMoreVideoDtoList", learnMoreVideoService.findLearnMoreList(pageable, searchDto));

        return "learnMoreVideo/list";
    }

    // Form
    @GetMapping("/form{idx}")
    public String learnMoreVideoForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        LearnMoreVideoDto learnMoreVideoDto = learnMoreVideoService.findLearnMoreByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (learnMoreVideoDto.isAccess()) {
            learnMoreVideoDto = learnMoreVideoAttachedFileService.findAttachedFileByLearnMoreIdx(idx, learnMoreVideoDto);

            model.addAttribute("learnMoreVideoDto", learnMoreVideoDto);

            returnPage = "learnMoreVideo/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Video
    @GetMapping({"", "/"})
    public String learnMoreVideoVideo(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        LearnMoreVideoDto learnMoreVideoDto = null;

        learnMoreVideoDto = learnMoreVideoService.findLearnMoreByIdx(idx);
        learnMoreVideoDto = learnMoreVideoAttachedFileService.findAttachedFileByLearnMoreIdx(idx, learnMoreVideoDto);

        model.addAttribute("learnMoreVideoDto", learnMoreVideoDto);

        return "learnMoreVideo/read";
    }
}