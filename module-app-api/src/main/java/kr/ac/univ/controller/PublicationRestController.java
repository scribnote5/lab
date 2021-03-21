package kr.ac.univ.controller;


import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.publication.dto.PublicationDto;
import kr.ac.univ.publication.dto.PublicationSearchDto;
import kr.ac.univ.publication.service.PublicationAttachedFileService;
import kr.ac.univ.publication.service.PublicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/publications")
public class PublicationRestController {
    private final PublicationService publicationService;
    private final PublicationAttachedFileService publicationAttachedFileService;

    public PublicationRestController(PublicationService publicationService, PublicationAttachedFileService publicationAttachedFileService) {
        this.publicationService = publicationService;
        this.publicationAttachedFileService = publicationAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postPublication(@RequestBody @Valid PublicationDto publicationDto) {
        Long idx = publicationService.insertPublication(publicationDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putPublication(@PathVariable("idx") Long idx, @RequestBody @Valid PublicationDto publicationDto) {
        publicationService.updatePublication(idx, publicationDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deletePublication(@PathVariable("idx") Long idx) throws Exception {
        publicationService.deletePublicationByIdx(idx);
        publicationAttachedFileService.deleteAllAttachedFile(idx);

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

        publicationAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteFileList) throws Exception {
        publicationAttachedFileService.deleteAttachedFile(deleteFileList);

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    // List Scroll
    @GetMapping("/list_scroll")
    public ResponseEntity<?> publicationListScroll(@RequestParam(value = "lastIdx", defaultValue = "-1") Long lastIdx,
                                                   PublicationSearchDto publicationSearchDto,
                                                   Model model) {
        // 방어 코드: lastIdx가 들어오지 않은 경우 충분히 큰 값을 전달하면 된다.
        if (lastIdx == -1L) {
            lastIdx = publicationService.findMaxPublicationIdx();
        }

        return new ResponseEntity<>(publicationService.findPublicationListScroll(lastIdx, publicationSearchDto), HttpStatus.OK);
    }

}
