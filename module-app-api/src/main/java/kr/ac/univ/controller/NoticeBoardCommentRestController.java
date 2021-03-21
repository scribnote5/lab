package kr.ac.univ.controller;

import kr.ac.univ.noticeBoard.dto.NoticeBoardCommentDto;
import kr.ac.univ.noticeBoard.service.NoticeBoardCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notice-boards-comments")
public class NoticeBoardCommentRestController {
    private final NoticeBoardCommentService noticeBoardCommentService;

    public NoticeBoardCommentRestController(NoticeBoardCommentService noticeBoardCommentService) {
        this.noticeBoardCommentService = noticeBoardCommentService;
    }

    @PostMapping
    public ResponseEntity<?> postNoticeBoard(@RequestBody @Valid NoticeBoardCommentDto noticeBoardCommentDto) {
        Long idx = noticeBoardCommentService.insertNoticeBoardComment(noticeBoardCommentDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putNoticeBoard(@PathVariable("idx") Long idx, @RequestBody @Valid NoticeBoardCommentDto noticeBoardCommentDto) {
        noticeBoardCommentService.updateNoticeBoardComment(idx, noticeBoardCommentDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteNoticeBoard(@PathVariable("idx") Long idx) {
        noticeBoardCommentService.deleteNoticeBoardCommentByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}