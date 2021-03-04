package kr.ac.univ.controller;

import kr.ac.univ.noticeBoard.domain.NoticeBoardComment;
import kr.ac.univ.noticeBoard.dto.NoticeBoardCommentDto;
import kr.ac.univ.noticeBoard.service.NoticeBoardCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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