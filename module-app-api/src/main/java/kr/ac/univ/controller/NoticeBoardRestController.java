package kr.ac.univ.controller;

import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.noticeBoard.service.NoticeBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice-boards")
public class NoticeBoardRestController {
    private final NoticeBoardService noticeBoardService;

    public NoticeBoardRestController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @PostMapping
    public ResponseEntity<?> postNoticeBoard(@RequestBody NoticeBoard noticeBoard) {
        Long idx = noticeBoardService.insertNoticeBoard(noticeBoard);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putNoticeBoard(@PathVariable("idx") Long idx, @RequestBody NoticeBoard noticeBoard) {
        noticeBoardService.updateNoticeBoard(idx, noticeBoard);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteNoticeBoard(@PathVariable("idx") Long idx) {
        noticeBoardService.deleteNoticeBoardByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}