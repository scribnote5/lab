package kr.ac.univ.controller;

import kr.ac.univ.album.dto.AlbumDto;
import kr.ac.univ.album.service.AlbumAttachedFileService;
import kr.ac.univ.album.service.AlbumService;
import kr.ac.univ.introduction.dto.IntroductionDto;
import kr.ac.univ.introduction.service.IntroductionService;
import kr.ac.univ.introductionImage.dto.IntroductionImageDto;
import kr.ac.univ.introductionImage.service.IntroductionImageAttachedFileService;
import kr.ac.univ.introductionImage.service.IntroductionImageService;
import kr.ac.univ.learnMoreRead.dto.LearnMoreReadDto;
import kr.ac.univ.learnMoreRead.service.LearnMoreReadAttachedFileService;
import kr.ac.univ.learnMoreRead.service.LearnMoreReadService;
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
    private final IntroductionImageService introductionImageService;
    private final IntroductionImageAttachedFileService introductionImageAttachedFileService;
    private final ResearchFieldService researchFieldService;
    private final NoticeBoardService noticeBoardService;
    private final SeminarService seminarService;

    public MainController(AlbumService albumService, AlbumAttachedFileService albumAttachedFileService, IntroductionService introductionService,
                          IntroductionImageService introductionImageService, IntroductionImageAttachedFileService introductionImageAttachedFileService,
                          ResearchFieldService researchFieldService, NoticeBoardService noticeBoardService, SeminarService seminarService) {
        this.albumService = albumService;
        this.albumAttachedFileService = albumAttachedFileService;
        this.introductionService = introductionService;
        this.introductionImageService = introductionImageService;
        this.introductionImageAttachedFileService = introductionImageAttachedFileService;
        this.researchFieldService = researchFieldService;
        this.noticeBoardService = noticeBoardService;
        this.seminarService = seminarService;
    }

    // Home
    @GetMapping("")
    public String home(Model model) {
        IntroductionDto introductionDto = introductionService.findIntroductionByActiveStatusIs();

        List<IntroductionImageDto> introductionImageDtoList = introductionImageService.findIntroductionImageListByActiveStatusIs();
        for (IntroductionImageDto introductionImageDto : introductionImageDtoList) {
            introductionImageAttachedFileService.findAttachedFileByIntroductionImageIdx(introductionImageDto.getIdx(), introductionImageDto);
        }

        List<AlbumDto> albumDtoList = albumService.findAlbumListByActiveStatusIs();
        for (AlbumDto albumDto : albumDtoList) {
            albumAttachedFileService.findAttachedFileByAlbumIdx(albumDto.getIdx(), albumDto);
        }

        model.addAttribute("introductionDto", introductionDto);
        model.addAttribute("introductionImageDtoList", introductionImageDtoList);
        model.addAttribute("researchFieldDtoList", researchFieldService.findResearchFieldListByActiveStatusIs());
        model.addAttribute("albumDtoList", albumDtoList);
        model.addAttribute("noticeBoardDtoList", noticeBoardService.findNoticeBoardList());
        model.addAttribute("seminarDtoList", seminarService.findSeminarList());

        return "main/home";
    }
}