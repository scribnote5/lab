package kr.ac.univ.controller;

import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.seminar.dto.SeminarDto;
import kr.ac.univ.seminar.service.SeminarAttachedFileService;
import kr.ac.univ.seminar.service.SeminarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/seminars")
public class SeminarRestController {
    private final SeminarService seminarService;
    private final SeminarAttachedFileService seminarAttachedFileService;

    public SeminarRestController(SeminarService seminarService, SeminarAttachedFileService seminarAttachedFileService) {
        this.seminarService = seminarService;
        this.seminarAttachedFileService = seminarAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postSeminar(@RequestBody @Valid SeminarDto seminarDto) {
          Long idx = seminarService.insertSeminar(seminarDto);


        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putSeminar(@PathVariable("idx") Long idx, @RequestBody @Valid SeminarDto seminarDto) {
        seminarService.updateSeminar(idx, seminarDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteSeminar(@PathVariable("idx") Long idx) throws Exception {
        seminarService.deleteSeminarByIdx(idx);
        seminarAttachedFileService.deleteAllAttachedFile(idx);

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

        seminarAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        seminarAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}