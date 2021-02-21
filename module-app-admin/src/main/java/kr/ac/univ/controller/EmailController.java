package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.email.dto.EmailDto;
import kr.ac.univ.email.service.EmailService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    // List
    @GetMapping("/list")
    public String emailList(Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("emailDtoList", emailService.findEmailList(pageable, searchDto));

        return "email/list";
    }

    // Form
    @GetMapping("/form{idx}")
    public String emailForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        EmailDto emailDto = emailService.findEmailByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (emailDto.isAccess()) {
            model.addAttribute("emailDto", emailDto);

            returnPage = "email/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String emailRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        EmailDto emailDto = emailService.findEmailByIdx(idx);

        model.addAttribute("emailDto", emailDto);

        return "email/read";
    }
}