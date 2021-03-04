package kr.ac.univ.controller;

import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.guestBook.dto.GuestBookDto;
import kr.ac.univ.guestBook.service.GuestBookAttachedFileService;
import kr.ac.univ.guestBook.service.GuestBookCommentService;
import kr.ac.univ.guestBook.service.GuestBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/guest-books")
public class GuestBookRestController {
    private final GuestBookService guestBookService;
    private final GuestBookCommentService guestBookCommentService;
    private final GuestBookAttachedFileService guestBookAttachedFileService;

    public GuestBookRestController(GuestBookService guestBookService, GuestBookCommentService guestBookCommentService, GuestBookAttachedFileService guestBookAttachedFileService) {
        this.guestBookService = guestBookService;
        this.guestBookCommentService = guestBookCommentService;
        this.guestBookAttachedFileService = guestBookAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postGuestBook(@RequestBody @Valid GuestBookDto guestBookDto) {
        Long idx = guestBookService.insertGuestBook(guestBookDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putGuestBook(@PathVariable("idx") Long idx, @RequestBody @Valid GuestBookDto guestBookDto) {
        guestBookService.updateGuestBook(idx, guestBookDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteGuestBook(@PathVariable("idx") Long idx) throws Exception {
        guestBookService.deleteGuestBookByIdx(idx);
        guestBookAttachedFileService.deleteAllAttachedFile(idx);
        guestBookCommentService.deleteAllByGuestBookIdx(idx);

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

        guestBookAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        guestBookAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}