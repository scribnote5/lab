package kr.ac.univ.controller;

import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.noticeBoard.dto.NoticeBoardDto;
import kr.ac.univ.noticeBoard.service.NoticeBoardAttachedFileService;
import kr.ac.univ.noticeBoard.service.NoticeBoardCommentService;
import kr.ac.univ.noticeBoard.service.NoticeBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/notice-boards")
public class NoticeBoardRestController {
    private final NoticeBoardService noticeBoardService;
    private final NoticeBoardCommentService noticeBoardCommentService;
    private final NoticeBoardAttachedFileService noticeBoardAttachedFileService;

    public NoticeBoardRestController(NoticeBoardService noticeBoardService, NoticeBoardCommentService noticeBoardCommentService, NoticeBoardAttachedFileService noticeBoardAttachedFileService) {
        this.noticeBoardService = noticeBoardService;
        this.noticeBoardCommentService = noticeBoardCommentService;
        this.noticeBoardAttachedFileService = noticeBoardAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postNoticeBoard(@RequestBody @Valid NoticeBoardDto noticeBoardDto) {
        Long idx = noticeBoardService.insertNoticeBoard(noticeBoardDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putNoticeBoard(@PathVariable("idx") Long idx, @RequestBody @Valid NoticeBoardDto noticeBoardDto) {
        noticeBoardService.updateNoticeBoard(idx, noticeBoardDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteNoticeBoard(@PathVariable("idx") Long idx) throws Exception {
        noticeBoardService.deleteNoticeBoardByIdx(idx);
        noticeBoardAttachedFileService.deleteAllAttachedFile(idx);
        noticeBoardCommentService.deleteAllByNoticeBoardIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    // 첨부 파일 업로드
    @PostMapping("/attachedFile")
    public ResponseEntity<?> uploadAttachedFile(Long idx, String createdBy, MultipartFile[] files) throws Exception {
        String fileValidationResult = FileValidator.isFileValid(files);

        // 파일 mime type 검사
        if (!"valid".equals(fileValidationResult)) {
            throw new FileTypeException(fileValidationResult);
        }

        noticeBoardAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        noticeBoardAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}