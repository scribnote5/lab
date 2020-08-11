package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.noticeBoard.dto.NoticeBoardDto;
import kr.ac.univ.noticeBoard.service.NoticeBoardAttachedFileService;
import kr.ac.univ.noticeBoard.service.NoticeBoardService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notice-board")
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;
    private final NoticeBoardAttachedFileService noticeBoardAttachedFileService;

    public NoticeBoardController(NoticeBoardService noticeBoardService, NoticeBoardAttachedFileService noticeBoardAttachedFileService) {
        this.noticeBoardService = noticeBoardService;
        this.noticeBoardAttachedFileService = noticeBoardAttachedFileService;
    }

    // List
    @GetMapping("/list")
    public String noticeBoardList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("noticeBoardDtoList", noticeBoardService.findNoticeBoardList(pageable, searchDto));

        return "/noticeBoard/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String noticeBoardForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        NoticeBoardDto noticeBoardDto = null;

        noticeBoardDto = noticeBoardService.findNoticeBoardByIdx(idx);
        noticeBoardDto = noticeBoardAttachedFileService.findAttachedFileByNoticeBoardIdx(idx, noticeBoardDto);

        model.addAttribute("noticeBoardDto", noticeBoardDto);

        return "/noticeBoard/form";
    }

    // Read
    @GetMapping({"", "/"})
    public String noticeBoardRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        NoticeBoardDto noticeBoardDto = null;

        noticeBoardDto = noticeBoardService.findNoticeBoardByIdx(idx);
        noticeBoardDto = noticeBoardAttachedFileService.findAttachedFileByNoticeBoardIdx(idx, noticeBoardDto);

        model.addAttribute("noticeBoardDto", noticeBoardDto);

        return "/noticeBoard/read";
    }
}