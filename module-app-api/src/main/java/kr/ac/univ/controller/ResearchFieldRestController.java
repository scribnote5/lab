package kr.ac.univ.controller;

import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.FileNumberExceededException;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.researchField.dto.ResearchFieldDto;
import kr.ac.univ.researchField.service.ResearchFieldAttachedFileService;
import kr.ac.univ.researchField.service.ResearchFieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/research-fields")
public class ResearchFieldRestController {
    private final ResearchFieldService researchFieldService;
    private final ResearchFieldAttachedFileService researchFieldAttachedFileService;

    public ResearchFieldRestController(ResearchFieldService researchFieldService, ResearchFieldAttachedFileService researchFieldAttachedFileService) {
        this.researchFieldService = researchFieldService;
        this.researchFieldAttachedFileService = researchFieldAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postResearchField(@RequestBody @Valid ResearchFieldDto researchFieldDto) {
        Long idx = researchFieldService.insertResearchField(researchFieldDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putResearchField(@PathVariable("idx") Long idx, @RequestBody @Valid ResearchFieldDto researchFieldDto) {
        researchFieldService.updateResearchField(idx, researchFieldDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteResearchField(@PathVariable("idx") Long idx) throws Exception {
        researchFieldService.deleteResearchFieldByIdx(idx);
        researchFieldAttachedFileService.deleteAllAttachedFile(idx);

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

        researchFieldAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        researchFieldAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}