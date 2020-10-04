package kr.ac.univ.controller;

import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.researchField.dto.ResearchFieldDto;
import kr.ac.univ.researchField.service.ResearchFieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/research-fields")
public class ResearchFieldRestController {
    private final ResearchFieldService researchFieldService;

    public ResearchFieldRestController(ResearchFieldService researchFieldService) {
        this.researchFieldService = researchFieldService;
    }

    @PostMapping
    public ResponseEntity<?> postResearchField(@RequestBody @Valid ResearchFieldDto researchFieldDto) {
        Long idx = researchFieldService.insertResearchField(researchFieldDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putResearchField(@PathVariable("idx") Long idx, @RequestBody @Valid ResearchFieldDto researchFieldDto) {
        researchFieldService.updateResearchField(idx, researchFieldDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteResearchField(@PathVariable("idx") Long idx) throws Exception {
        researchFieldService.deleteResearchFieldByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}