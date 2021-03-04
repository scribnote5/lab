package kr.ac.univ.controller;

import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.maintenance.dto.MaintenanceDto;
import kr.ac.univ.maintenance.service.MaintenanceAttachedFileService;
import kr.ac.univ.maintenance.service.MaintenanceCommentService;
import kr.ac.univ.maintenance.service.MaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceRestController {
    private final MaintenanceService maintenanceService;
    private final MaintenanceCommentService maintenanceCommentService;
    private final MaintenanceAttachedFileService maintenanceAttachedFileService;

    public MaintenanceRestController(MaintenanceService maintenanceService, MaintenanceCommentService maintenanceCommentService, MaintenanceAttachedFileService maintenanceAttachedFileService) {
        this.maintenanceService = maintenanceService;
        this.maintenanceCommentService = maintenanceCommentService;
        this.maintenanceAttachedFileService = maintenanceAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postMaintenance(@RequestBody @Valid MaintenanceDto maintenanceDto) {
        System.out.println(maintenanceDto);

        Long idx = maintenanceService.insertMaintenance(maintenanceDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putMaintenance(@PathVariable("idx") Long idx, @RequestBody @Valid MaintenanceDto maintenanceDto) {
        maintenanceService.updateMaintenance(idx, maintenanceDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteMaintenance(@PathVariable("idx") Long idx) throws Exception {
        maintenanceService.deleteMaintenanceByIdx(idx);
        maintenanceAttachedFileService.deleteAllAttachedFile(idx);
        maintenanceCommentService.deleteAllByMaintenanceIdx(idx);

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

        maintenanceAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        maintenanceAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}