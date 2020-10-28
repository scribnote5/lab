package kr.ac.univ.controller;

import kr.ac.univ.email.dto.EmailDto;
import kr.ac.univ.email.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/emails")
public class EmailRestController {
    private final EmailService emailService;

    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> postEmail(@RequestBody @Valid EmailDto emailDto) {
        Long idx = emailService.insertEmail(emailDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putEmail(@PathVariable("idx") Long idx, @RequestBody @Valid EmailDto emailDto) {
        emailService.updateEmail(idx, emailDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteEmail(@PathVariable("idx") Long idx) throws Exception {
        emailService.deleteEmailByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}