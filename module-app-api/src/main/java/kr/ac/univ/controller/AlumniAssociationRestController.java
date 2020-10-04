package kr.ac.univ.controller;

import kr.ac.univ.alumniAssociation.dto.AlumniAssociationDto;
import kr.ac.univ.alumniAssociation.service.AlumniAssociationService;
import kr.ac.univ.error.ErrorCode;
import kr.ac.univ.exception.BusinessException;
import kr.ac.univ.util.EmptyUtil;
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
        if (alumniAssociationDto.getMainPagePriority() != -1 && !EmptyUtil.isEmpty(alumniAssociationService.findByMainPagePriorityIs(alumniAssociationDto.getMainPagePriority()))) {
            throw new BusinessException(ErrorCode.MAIN_PAGE_PRIORITY_DUPLICATE);
        }

        Long idx = alumniAssociationService.insertAlumniAssociation(alumniAssociationDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putAlumniAssociation(@PathVariable("idx") Long idx, @RequestBody @Valid AlumniAssociationDto alumniAssociationDto) {
        if (alumniAssociationDto.getMainPagePriority() != -1 && !EmptyUtil.isEmpty(alumniAssociationService.findByMainPagePriorityIs(alumniAssociationDto.getMainPagePriority()))) {
            throw new BusinessException(ErrorCode.MAIN_PAGE_PRIORITY_DUPLICATE);
        }

        alumniAssociationService.updateAlumniAssociation(idx, alumniAssociationDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteAlumniAssociation(@PathVariable("idx") Long idx) throws Exception {
        alumniAssociationService.deleteAlumniAssociationByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}