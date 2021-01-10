package kr.ac.univ.controller;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.exception.BusinessException;
import kr.ac.univ.setting.dto.SettingDto;
import kr.ac.univ.setting.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/settings")
public class SettingRestController {
    private final SettingService settingService;

    public SettingRestController(SettingService settingService) {
        this.settingService = settingService;
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putSetting(@PathVariable("idx") Long idx, @RequestBody @Valid SettingDto settingDto) {
        settingService.updateSetting(idx, settingDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}