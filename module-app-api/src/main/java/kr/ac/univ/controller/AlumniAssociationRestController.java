package kr.ac.univ.controller;

import kr.ac.univ.alumniAssociation.dto.AlumniAssociationDto;
import kr.ac.univ.alumniAssociation.service.AlumniAssociationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/alumni-associations")
public class AlumniAssociationRestController {
    private final AlumniAssociationService alumniAssociationService;

    public AlumniAssociationRestController(AlumniAssociationService alumniAssociationService) {
        this.alumniAssociationService = alumniAssociationService;
    }

    @PostMapping
    public ResponseEntity<?> postAlumniAssociation(@RequestBody @Valid AlumniAssociationDto alumniAssociationDto) {
        Long idx = alumniAssociationService.insertAlumniAssociation(alumniAssociationDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putAlumniAssociation(@PathVariable("idx") Long idx, @RequestBody @Valid AlumniAssociationDto alumniAssociationDto) {
        alumniAssociationService.updateAlumniAssociation(idx, alumniAssociationDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteAlumniAssociation(@PathVariable("idx") Long idx) {
        alumniAssociationService.deleteAlumniAssociationByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}