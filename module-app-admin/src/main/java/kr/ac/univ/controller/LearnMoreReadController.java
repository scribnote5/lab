package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.exception.BusinessException;
import kr.ac.univ.learnMoreRead.dto.LearnMoreReadDto;
import kr.ac.univ.learnMoreRead.service.LearnMoreReadAttachedFileService;
import kr.ac.univ.learnMoreRead.service.LearnMoreReadService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/learn-more-read")
public class LearnMoreReadController {
    private final LearnMoreReadService learnMoreReadService;
    private final LearnMoreReadAttachedFileService learnMoreReadAttachedFileService;

    public LearnMoreReadController(LearnMoreReadService learnMoreReadService, LearnMoreReadAttachedFileService learnMoreReadAttachedFileService) {
        this.learnMoreReadService = learnMoreReadService;
        this.learnMoreReadAttachedFileService = learnMoreReadAttachedFileService;
    }

    // List
    @GetMapping("/list")
    public String learnMoreReadList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        if ("DOWNLOAD_FILE_TYPE".equals(searchDto.getSearchType()) && !("READ".equals(searchDto.getKeyword()) || "VIDEO".equals(searchDto.getKeyword()))) {
            throw new BusinessException("Please Check keyword 'READ' or 'VIDEO'.");
        }

        model.addAttribute("learnMoreReadDtoList", learnMoreReadService.findLearnMoreList(pageable, searchDto));

        return "learnMoreRead/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String learnMoreReadForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        LearnMoreReadDto learnMoreReadDto = learnMoreReadService.findLearnMoreByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (learnMoreReadDto.isAccess()) {
            learnMoreReadDto = learnMoreReadAttachedFileService.findAttachedFileByLearnMoreIdx(idx, learnMoreReadDto);

            model.addAttribute("learnMoreReadDto", learnMoreReadDto);

            returnPage = "learnMoreRead/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String learnMoreReadRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        LearnMoreReadDto learnMoreReadDto = null;

        learnMoreReadDto = learnMoreReadService.findLearnMoreByIdx(idx);
        learnMoreReadDto = learnMoreReadAttachedFileService.findAttachedFileByLearnMoreIdx(idx, learnMoreReadDto);

        model.addAttribute("learnMoreReadDto", learnMoreReadDto);

        return "learnMoreRead/read";
    }
}