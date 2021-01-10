package kr.ac.univ.controller;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.BusinessException;
import kr.ac.univ.exception.FileNumberExceededException;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.learnMoreVideo.dto.LearnMoreVideoDto;
import kr.ac.univ.learnMoreVideo.service.LearnMoreVideoAttachedFileService;
import kr.ac.univ.learnMoreVideo.service.LearnMoreVideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/learn-more-videos")
public class LearnMoreVideoRestController {
    private final LearnMoreVideoService learnMoreVideoService;
    private final LearnMoreVideoAttachedFileService learnMoreVideoAttachedFileService;

    public LearnMoreVideoRestController(LearnMoreVideoService learnMoreVideoService, LearnMoreVideoAttachedFileService learnMoreVideoAttachedFileService) {
        this.learnMoreVideoService = learnMoreVideoService;
        this.learnMoreVideoAttachedFileService = learnMoreVideoAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postLearnMore(@RequestBody @Valid LearnMoreVideoDto learnMoreVideoDto) {
        if (learnMoreVideoDto.getActiveStatus() == ActiveStatus.ACTIVE && learnMoreVideoService.countAllByActiveStatus() > 0) {
            throw new BusinessException("Only one learn more video must be activate.");
        }

        Long idx = learnMoreVideoService.insertLearnMore(learnMoreVideoDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putLearnMore(@PathVariable("idx") Long idx, @RequestBody @Valid LearnMoreVideoDto learnMoreVideoDto) {
        if (learnMoreVideoDto.getActiveStatus() == ActiveStatus.ACTIVE && learnMoreVideoService.countAllByActiveStatus() > 1) {
            throw new BusinessException("Only one learn more video must be activate.");
        }

        learnMoreVideoService.updateLearnMore(idx, learnMoreVideoDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteLearnMore(@PathVariable("idx") Long idx) throws Exception {
        learnMoreVideoService.deleteLearnMoreByIdx(idx);
        learnMoreVideoAttachedFileService.deleteAllAttachedFile(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    // 첨부 파일 업로드
    @PostMapping("/attachedFile")
    public ResponseEntity<?> uploadAttachedFile(Long idx, String createdBy, MultipartFile[] files) throws Exception {
        if (files.length >= 2) {
            throw new FileNumberExceededException("The number of files that must be uploaded is 1.");
        }

        String fileValidationResult = FileValidator.isFileValid(files);

        // 파일 mime type 검사
        if (!"valid".equals(fileValidationResult)) {
            throw new FileTypeException(fileValidationResult);
        }

        learnMoreVideoAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        learnMoreVideoAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}