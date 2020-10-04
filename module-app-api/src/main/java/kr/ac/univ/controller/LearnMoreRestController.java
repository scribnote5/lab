package kr.ac.univ.controller;

import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.FileNumberExceededException;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.learnMore.dto.LearnMoreDto;
import kr.ac.univ.learnMore.service.LearnMoreAttachedFileService;
import kr.ac.univ.learnMore.service.LearnMoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/learn-mores")
public class LearnMoreRestController {
    private final LearnMoreService learnMoreService;
    private final LearnMoreAttachedFileService learnMoreAttachedFileService;

    public LearnMoreRestController(LearnMoreService learnMoreService, LearnMoreAttachedFileService learnMoreAttachedFileService) {
        this.learnMoreService = learnMoreService;
        this.learnMoreAttachedFileService = learnMoreAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postLearnMore(@RequestBody @Valid LearnMoreDto learnMoreDto) {
        Long idx = learnMoreService.insertLearnMore(learnMoreDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putLearnMore(@PathVariable("idx") Long idx, @RequestBody @Valid LearnMoreDto learnMoreDto) {
        learnMoreService.updateLearnMore(idx, learnMoreDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteLearnMore(@PathVariable("idx") Long idx) throws Exception {
        learnMoreService.deleteLearnMoreByIdx(idx);
        learnMoreAttachedFileService.deleteAllAttachedFile(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    // 첨부 파일 업로드
    @PostMapping("/attachedFile")
    public ResponseEntity<?> uploadAttachedFile(Long idx, String createdBy, MultipartFile[] files) throws Exception {
        if (files.length >= 2) {
            throw new FileNumberExceededException("The number of files that can be uploaded is 1.");
        }

        String fileValidationResult = FileValidator.isFileValid(files);

        // 파일 mime type 검사
        if (!"valid".equals(fileValidationResult)) {
            throw new FileTypeException(fileValidationResult);
        }

        learnMoreAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        learnMoreAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}