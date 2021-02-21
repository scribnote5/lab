package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.loginHistory.dto.LoginHistoryDto;
import kr.ac.univ.loginHistory.service.LoginHistoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login-history")
public class LoginHistoryController {
    private final LoginHistoryService loginHistoryService;

    public LoginHistoryController(LoginHistoryService loginHistoryService) {
        this.loginHistoryService = loginHistoryService;
    }

    // List
    @GetMapping("/list")
    public String loginHistoryList(Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("loginHistoryDtoList", loginHistoryService.findLoginHistoryList(pageable, searchDto));

        return "loginHistory/list";
    }

    // Read
    @GetMapping({"", "/"})
    public String loginHistoryRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        LoginHistoryDto loginHistoryDto = loginHistoryService.findLoginHistoryByIdx(idx);

        model.addAttribute("loginHistoryDto", loginHistoryDto);

        return "loginHistory/read";
    }
}