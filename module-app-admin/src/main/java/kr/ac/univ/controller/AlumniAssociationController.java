package kr.ac.univ.controller;

import kr.ac.univ.alumniAssociation.dto.AlumniAssociationDto;
import kr.ac.univ.alumniAssociation.service.AlumniAssociationService;
import kr.ac.univ.common.dto.SearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/alumni-association")
public class AlumniAssociationController {
    private final AlumniAssociationService alumniAssociationService;

    public AlumniAssociationController(AlumniAssociationService alumniAssociationService) {
        this.alumniAssociationService = alumniAssociationService;
    }

    // List
    @GetMapping("/list")
    public String alumniAssociationList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("alumniAssociationDtoList", alumniAssociationService.findAlumniAssociationList(pageable, searchDto));

        return "alumniAssociation/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String alumniAssociationForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        AlumniAssociationDto alumniAssociationDto = alumniAssociationService.findAlumniAssociationByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (alumniAssociationDto.isAccess()) {
            model.addAttribute("alumniAssociationDto", alumniAssociationDto);

            returnPage = "alumniAssociation/form";
        } else {
            returnPage = "user/permission-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String alumniAssociationRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        AlumniAssociationDto alumniAssociationDto = null;

        alumniAssociationDto = alumniAssociationService.findAlumniAssociationByIdx(idx);

        model.addAttribute("alumniAssociationDto", alumniAssociationDto);

        return "alumniAssociation/read";
    }
}