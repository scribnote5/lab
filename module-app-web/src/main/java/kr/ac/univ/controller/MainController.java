package kr.ac.univ.controller;

import kr.ac.univ.album.dto.AlbumDto;
import kr.ac.univ.album.service.AlbumAttachedFileService;
import kr.ac.univ.album.service.AlbumService;
import kr.ac.univ.introduction.service.IntroductionService;
import kr.ac.univ.learnMore.dto.LearnMoreDto;
import kr.ac.univ.learnMore.service.LearnMoreAttachedFileService;
import kr.ac.univ.learnMore.service.LearnMoreService;
import kr.ac.univ.noticeBoard.service.NoticeBoardService;
import kr.ac.univ.researchField.service.ResearchFieldService;
import kr.ac.univ.seminar.service.SeminarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final AlbumService albumService;
    private final AlbumAttachedFileService albumAttachedFileService;
    private final IntroductionService introductionService;
    private final LearnMoreService learnMoreService;
    private final LearnMoreAttachedFileService learnMoreAttachedFileService;
    private final ResearchFieldService researchFieldService;
    private final NoticeBoardService noticeBoardService;
    private final SeminarService seminarService;

    public MainController(AlbumService albumService, AlbumAttachedFileService albumAttachedFileService, IntroductionService introductionService, LearnMoreService learnMoreService, LearnMoreAttachedFileService learnMoreAttachedFileService,
                          ResearchFieldService researchFieldService, NoticeBoardService noticeBoardService, SeminarService seminarService) {
        this.albumService = albumService;
        this.albumAttachedFileService = albumAttachedFileService;
        this.introductionService = introductionService;
        this.learnMoreService = learnMoreService;
        this.learnMoreAttachedFileService = learnMoreAttachedFileService;
        this.researchFieldService = researchFieldService;
        this.noticeBoardService = noticeBoardService;
        this.seminarService = seminarService;
    }

    // Home
    @GetMapping("")
    public String home(Model model) {
        List<LearnMoreDto> learMoreDtoList = learnMoreService.findAllByActiveStatusIs();
        for(LearnMoreDto learnMoreDto: learMoreDtoList) {
            learnMoreAttachedFileService.findAttachedFileByLearnMoreIdx(learnMoreDto.getIdx(), learnMoreDto);
        }

        List<AlbumDto> albumDtoList = albumService.findAlbumListByActiveStatusIs();
        for(AlbumDto albumDto: albumDtoList) {
            albumAttachedFileService.findAttachedFileByAlbumIdx(albumDto.getIdx(), albumDto);
        }

        System.out.println("Data");
        System.out.println(albumService.findAlbumListByActiveStatusIs());

        model.addAttribute("albumDtoList", albumDtoList);
        model.addAttribute("introductionDto", introductionService.findIntroductionByActiveStatusIs());
        model.addAttribute("researchFieldDtoList", researchFieldService.findResearchFieldListByActiveStatusIs());
        model.addAttribute("learnMoreDtoList", learMoreDtoList);
        model.addAttribute("noticeBoardDtoList", noticeBoardService.findNoticeBoardList());
        model.addAttribute("seminarDtoList", seminarService.findSeminarList());

        return "/main/home";
    }
}