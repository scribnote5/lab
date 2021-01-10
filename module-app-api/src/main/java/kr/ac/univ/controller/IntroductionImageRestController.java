package kr.ac.univ.controller;

import kr.ac.univ.introductionImage.dto.IntroductionImageDto;
import kr.ac.univ.introductionImage.service.IntroductionImageAttachedFileService;
import kr.ac.univ.introductionImage.service.IntroductionImageService;
import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.error.ErrorCode;
import kr.ac.univ.exception.BusinessException;
import kr.ac.univ.exception.FileNumberExceededException;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.util.EmptyUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/introduction-images")
public class IntroductionImageRestController {
    private final IntroductionImageService introductionImageService;
    private final IntroductionImageAttachedFileService introductionImageAttachedFileService;

    public IntroductionImageRestController(IntroductionImageService introductionImageService, IntroductionImageAttachedFileService introductionImageAttachedFileService) {
        this.introductionImageService = introductionImageService;
        this.introductionImageAttachedFileService = introductionImageAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postIntroductionImage(@RequestBody @Valid IntroductionImageDto introductionImageDto) {
        if(introductionImageDto.getMainPagePriority() != - 1 && !EmptyUtil.isEmpty(introductionImageService.findByMainPagePriorityIs(introductionImageDto.getIdx(), introductionImageDto.getMainPagePriority())))  {
            throw new BusinessException(ErrorCode.MAIN_PAGE_PRIORITY_DUPLICATE);
        }

        Long idx = introductionImageService.insertIntroductionImage(introductionImageDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putIntroductionImage(@PathVariable("idx") Long idx, @RequestBody @Valid IntroductionImageDto introductionImageDto) {
        if(introductionImageDto.getMainPagePriority() != - 1 && !EmptyUtil.isEmpty(introductionImageService.findByMainPagePriorityIs(introductionImageDto.getIdx(), introductionImageDto.getMainPagePriority())))  {
            throw new BusinessException(ErrorCode.MAIN_PAGE_PRIORITY_DUPLICATE);
        }

        introductionImageService.updateIntroductionImage(idx, introductionImageDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteIntroductionImage(@PathVariable("idx") Long idx) throws Exception {
        introductionImageService.deleteIntroductionImageByIdx(idx);
        introductionImageAttachedFileService.deleteAllAttachedFile(idx);

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

        introductionImageAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        introductionImageAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}