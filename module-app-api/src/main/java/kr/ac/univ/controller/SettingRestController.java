package kr.ac.univ.controller;

import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.setting.dto.SettingDto;
import kr.ac.univ.setting.service.SettingAttachedFileService;
import kr.ac.univ.setting.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/settings")
public class SettingRestController {
    private final SettingService settingService;
    private final SettingAttachedFileService settingAttachedFileService;

    public SettingRestController(SettingService settingService, SettingAttachedFileService settingAttachedFileService) {
        this.settingService = settingService;
        this.settingAttachedFileService = settingAttachedFileService;
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putSetting(@PathVariable("idx") Long idx, @RequestBody @Valid SettingDto settingDto) {
        settingService.updateSetting(idx, settingDto);

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

        settingAttachedFileService.uploadAttachedFile(files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
}