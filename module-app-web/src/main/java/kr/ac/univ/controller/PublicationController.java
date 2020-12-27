package kr.ac.univ.controller;

import kr.ac.univ.publication.dto.PublicationSearchDto;
import kr.ac.univ.publication.service.PublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publication")
public class PublicationController {
    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    // List Scroll
    @GetMapping("/list_scroll")
    public String publicationListScroll(PublicationSearchDto publicationSearchDto, Model model) {
        // 방어 코드: lastIdx는 충분히 큰 값을 전달하면 된다.
        Long lastIdx = publicationService.findMaxPublicationIdx();

        model.addAttribute("publicationDtoList", publicationService.findPublicationListScroll(lastIdx, publicationSearchDto));

        return "publication/list_scroll";
    }
}