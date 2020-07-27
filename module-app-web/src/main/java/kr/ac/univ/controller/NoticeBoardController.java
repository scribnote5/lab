package kr.ac.univ.controller;

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

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    // List
    @GetMapping("/list")
    public String noticeBoardList(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("noticeBoardList", noticeBoardService.findNoticeBoardList(pageable));

        return "/noticeBoard/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String noticeBoardForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        model.addAttribute("noticeBoard", noticeBoardService.findNoticeBoardByIdx(idx));

        return "/noticeBoard/form";
    }

    // Read
    @GetMapping({"", "/"})
    public String noticeBoardRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        model.addAttribute("noticeBoard", noticeBoardService.findNoticeBoardByIdx(idx));

        return "/noticeBoard/read";
    }
}