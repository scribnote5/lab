package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.guestBook.dto.GuestBookCommentDto;
import kr.ac.univ.guestBook.dto.GuestBookDto;
import kr.ac.univ.guestBook.service.GuestBookAttachedFileService;
import kr.ac.univ.guestBook.service.GuestBookCommentService;
import kr.ac.univ.guestBook.service.GuestBookService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/guest-book")
public class GuestBookController {
    private final GuestBookService guestBookService;
    private final GuestBookAttachedFileService guestBookAttachedFileService;
    private final GuestBookCommentService guestBookCommentService;

    public GuestBookController(GuestBookService guestBookService, GuestBookAttachedFileService guestBookAttachedFileService, GuestBookCommentService guestBookCommentService) {
        this.guestBookService = guestBookService;
        this.guestBookAttachedFileService = guestBookAttachedFileService;
        this.guestBookCommentService = guestBookCommentService;
    }

    // List
    @GetMapping("/list")
    public String guestBookList(Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("guestBookDtoList", guestBookService.findGuestBookList(pageable, searchDto));

        return "guestBook/list";
    }

    // Form
    @GetMapping("/form{idx}")
    public String guestBookForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        GuestBookDto guestBookDto = guestBookService.findGuestBookByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (guestBookDto.isAccess()) {
            guestBookDto = guestBookAttachedFileService.findAttachedFileByGuestBookIdx(idx, guestBookDto);

            model.addAttribute("guestBookDto", guestBookDto);
            model.addAttribute("guestBookDtoList", guestBookService.findAllByActiveStatusIsAndMainPagePriorityLessThanEqualOrderByMainPagePriorityAsc());

            returnPage = "guestBook/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String guestBookRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        GuestBookDto guestBookDto = null;
        List<GuestBookCommentDto> guestBookCommentDtoList = null;

        guestBookDto = guestBookService.findGuestBookByIdx(idx);
        guestBookDto = guestBookAttachedFileService.findAttachedFileByGuestBookIdx(idx, guestBookDto);
        guestBookCommentDtoList = guestBookCommentService.findAllByGuestBookIdxOrderByCreatedDateDesc(idx);

        model.addAttribute("guestBookDto", guestBookDto);
        model.addAttribute("guestBookCommentDtoList", guestBookCommentDtoList);

        return "guestBook/read";
    }
}