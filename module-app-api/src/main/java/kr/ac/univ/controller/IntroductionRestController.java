package kr.ac.univ.controller;

import kr.ac.univ.introduction.dto.IntroductionDto;
import kr.ac.univ.introduction.service.IntroductionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/introductions")
public class IntroductionRestController {
    private final IntroductionService introductionService;

    public IntroductionRestController(IntroductionService introductionService) {
        this.introductionService = introductionService;
    }

    @PostMapping
    public ResponseEntity<?> postIntroduction(@RequestBody @Valid IntroductionDto introductionDto) {
        Long idx = introductionService.insertIntroduction(introductionDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putIntroduction(@PathVariable("idx") Long idx, @RequestBody @Valid IntroductionDto introductionDto) {
        introductionService.updateIntroduction(idx, introductionDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteIntroduction(@PathVariable("idx") Long idx) throws Exception {
        introductionService.deleteIntroductionByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}