package kr.ac.univ.controller;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.BusinessException;
import kr.ac.univ.exception.FileNumberExceededException;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.learnMoreRead.dto.LearnMoreReadDto;
import kr.ac.univ.learnMoreRead.service.LearnMoreReadAttachedFileService;
import kr.ac.univ.learnMoreRead.service.LearnMoreReadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/learn-more-reads")
public class LearnMoreReadRestController {
    private final LearnMoreReadService learnMoreReadService;
    private final LearnMoreReadAttachedFileService learnMoreReadAttachedFileService;

    public LearnMoreReadRestController(LearnMoreReadService learnMoreReadService, LearnMoreReadAttachedFileService learnMoreReadAttachedFileService) {
        this.learnMoreReadService = learnMoreReadService;
        this.learnMoreReadAttachedFileService = learnMoreReadAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postLearnMore(@RequestBody @Valid LearnMoreReadDto learnMoreReadDto) {
        if (learnMoreReadDto.getActiveStatus() == ActiveStatus.ACTIVE && learnMoreReadService.countAllByActiveStatus() > 0) {
            throw new BusinessException("Only one learnMoreRead can be activate.");
        }

        Long idx = learnMoreReadService.insertLearnMore(learnMoreReadDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putLearnMore(@PathVariable("idx") Long idx, @RequestBody @Valid LearnMoreReadDto learnMoreReadDto) {
        if (learnMoreReadDto.getActiveStatus() == ActiveStatus.ACTIVE && learnMoreReadService.countAllByActiveStatus() > 1) {
            throw new BusinessException("Only one learnMoreRead can be activate.");
        }

        learnMoreReadService.updateLearnMore(idx, learnMoreReadDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteLearnMore(@PathVariable("idx") Long idx) throws Exception {
        learnMoreReadService.deleteLearnMoreByIdx(idx);
        learnMoreReadAttachedFileService.deleteAllAttachedFile(idx);

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

        learnMoreReadAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        learnMoreReadAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}