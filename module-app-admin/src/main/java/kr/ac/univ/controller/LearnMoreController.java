package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.exception.BusinessException;
import kr.ac.univ.learnMore.dto.LearnMoreDto;
import kr.ac.univ.learnMore.service.LearnMoreAttachedFileService;
import kr.ac.univ.learnMore.service.LearnMoreService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/learn-more")
public class LearnMoreController {
    private final LearnMoreService learnMoreService;
    private final LearnMoreAttachedFileService learnMoreAttachedFileService;

    public LearnMoreController(LearnMoreService learnMoreService, LearnMoreAttachedFileService learnMoreAttachedFileService) {
        this.learnMoreService = learnMoreService;
        this.learnMoreAttachedFileService = learnMoreAttachedFileService;
    }

    // List
    @GetMapping("/list")
    public String learnMoreList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        if ("DOWNLOAD_FILE_TYPE".equals(searchDto.getSearchType()) && !("READ".equals(searchDto.getKeyword()) || "VIDEO".equals(searchDto.getKeyword()))) {
            throw new BusinessException("Please Check keyword 'READ' or 'VIDEO'.");
        }

        model.addAttribute("learnMoreDtoList", learnMoreService.findLearnMoreList(pageable, searchDto));

        return "/learnMore/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String learnMoreForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        LearnMoreDto learnMoreDto = learnMoreService.findLearnMoreByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (learnMoreDto.isAccess()) {
            learnMoreDto = learnMoreAttachedFileService.findAttachedFileByLearnMoreIdx(idx, learnMoreDto);

            model.addAttribute("learnMoreDto", learnMoreDto);

            returnPage = "/learnMore/form";
        } else {
            returnPage = "/user/permission-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String learnMoreRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        LearnMoreDto learnMoreDto = null;

        learnMoreDto = learnMoreService.findLearnMoreByIdx(idx);
        learnMoreDto = learnMoreAttachedFileService.findAttachedFileByLearnMoreIdx(idx, learnMoreDto);

        model.addAttribute("learnMoreDto", learnMoreDto);

        return "/learnMore/read";
    }
}