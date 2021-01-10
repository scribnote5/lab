package kr.ac.univ.controller;

import kr.ac.univ.aboutUs.dto.AboutUsDto;
import kr.ac.univ.aboutUs.service.AboutUsService;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/about-us")
public class AboutUsRestController {
    private final AboutUsService aboutUsService;

    public AboutUsRestController(AboutUsService aboutUsService) {
        this.aboutUsService = aboutUsService;
    }

    @PostMapping
    public ResponseEntity<?> postAboutUs(@RequestBody @Valid AboutUsDto aboutUsDto) {
        if (aboutUsDto.getActiveStatus() == ActiveStatus.ACTIVE && aboutUsService.countAllByActiveStatus() > 0) {
            throw new BusinessException("Only one about us must be activate.");
        }

        Long idx = aboutUsService.insertAboutUs(aboutUsDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putAboutUs(@PathVariable("idx") Long idx, @RequestBody @Valid AboutUsDto aboutUsDto) {
        if (aboutUsDto.getActiveStatus() == ActiveStatus.ACTIVE && aboutUsService.countAllByActiveStatus() > 1) {
            throw new BusinessException("Only one about us must be activate.");
        }

        aboutUsService.updateAboutUs(idx, aboutUsDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteAboutUs(@PathVariable("idx") Long idx) throws Exception {
        aboutUsService.deleteAboutUsByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}