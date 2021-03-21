package kr.ac.univ.controller;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.loginHistory.service.LoginHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login-historys")
public class LoginHistoryRestController {
    private final LoginHistoryService loginHistoryService;

    public LoginHistoryRestController(LoginHistoryService loginHistoryService) {
        this.loginHistoryService = loginHistoryService;
    }

    @PutMapping("/{idx}/{activeStatus}")
    public ResponseEntity<?> putLoginHistory(@PathVariable("idx") Long idx, @PathVariable("activeStatus") ActiveStatus activeStatus) {
        loginHistoryService.updateLoginHistory(idx, activeStatus);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteLoginHistory(@PathVariable("idx") Long idx) {
        loginHistoryService.deleteLoginHistoryByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}