package kr.ac.univ.controller;

import kr.ac.univ.noticeBoard.dto.NoticeBoardDto;
import kr.ac.univ.noticeBoard.dto.mapper.NoticeBoardMapper;
import kr.ac.univ.noticeBoard.service.NoticeBoardAttachedFileService;
import kr.ac.univ.noticeBoard.service.NoticeBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/notice-boards")
public class NoticeBoardRestController {
    private final NoticeBoardService noticeBoardService;
    private final NoticeBoardAttachedFileService noticeBoardAttachedFileService;

    public NoticeBoardRestController(NoticeBoardService noticeBoardService, NoticeBoardAttachedFileService noticeBoardAttachedFileService) {
        this.noticeBoardService = noticeBoardService;
        this.noticeBoardAttachedFileService = noticeBoardAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postNoticeBoard(@RequestBody NoticeBoardDto noticeBoardDto) {
        Long idx = noticeBoardService.insertNoticeBoard(noticeBoardDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putNoticeBoard(@PathVariable("idx") Long idx, @RequestBody NoticeBoardDto noticeBoardDto) {
        noticeBoardService.updateNoticeBoard(idx, noticeBoardDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteNoticeBoard(@PathVariable("idx") Long idx) throws Exception {
        noticeBoardService.deleteNoticeBoardByIdx(idx);
        noticeBoardAttachedFileService.deleteAllAttachedFile(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    // 첨부 파일 업로드
    @PostMapping("/attachedFile")
    public ResponseEntity<?> uploadAttachedFile(Long idx, String createdBy, MultipartFile[] files) throws Exception {
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