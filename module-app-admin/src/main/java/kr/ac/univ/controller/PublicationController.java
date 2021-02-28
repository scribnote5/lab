package kr.ac.univ.controller;

import kr.ac.univ.publication.dto.PublicationDto;
import kr.ac.univ.publication.dto.PublicationSearchDto;
import kr.ac.univ.publication.service.PublicationAttachedFileService;
import kr.ac.univ.publication.service.PublicationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/publication")
public class PublicationController {
    private final PublicationService publicationService;
    private final PublicationAttachedFileService publicationAttachedFileService;

    public PublicationController(PublicationService publicationService, PublicationAttachedFileService publicationAttachedFileService) {
        this.publicationService = publicationService;
        this.publicationAttachedFileService = publicationAttachedFileService;
    }

    // List
    @GetMapping("/list")
    public String publicationList(Pageable pageable, PublicationSearchDto publicationSearchDto, Model model) {
        model.addAttribute("publicationDtoList", publicationService.findPublicationList(pageable, publicationSearchDto));

        return "publication/list";
    }

    // Form
    @GetMapping("/form{idx}")
    public String publicationForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        PublicationDto publicationDto = publicationService.findPublicationByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (publicationDto.isAccess()) {
            publicationDto = publicationAttachedFileService.findAttachedFileByPublicationIdx(idx, publicationDto);

            model.addAttribute("publicationDto", publicationDto);

            returnPage = "publication/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String publicationRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        PublicationDto publicationDto = null;

        publicationDto = publicationService.findPublicationByIdx(idx);
        publicationDto = publicationAttachedFileService.findAttachedFileByPublicationIdx(idx, publicationDto);

        model.addAttribute("publicationDto", publicationDto);

        return "publication/read";
    }

}