package kr.ac.univ.controller;

import kr.ac.univ.album.dto.AlbumDto;
import kr.ac.univ.album.service.AlbumAttachedFileService;
import kr.ac.univ.album.service.AlbumService;
import kr.ac.univ.event.service.EventService;
import kr.ac.univ.introduction.service.IntroductionService;
import kr.ac.univ.introductionImage.dto.IntroductionImageDto;
import kr.ac.univ.introductionImage.service.IntroductionImageAttachedFileService;
import kr.ac.univ.introductionImage.service.IntroductionImageService;
import kr.ac.univ.noticeBoard.service.NoticeBoardService;
import kr.ac.univ.researchField.dto.ResearchFieldDto;
import kr.ac.univ.researchField.service.ResearchFieldAttachedFileService;
import kr.ac.univ.researchField.service.ResearchFieldService;
import kr.ac.univ.seminar.service.SeminarService;
import kr.ac.univ.setting.service.SettingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final AlbumService albumService;
    private final AlbumAttachedFileService albumAttachedFileService;
    private final IntroductionService introductionService;
    private final IntroductionImageService introductionImageService;
    private final IntroductionImageAttachedFileService introductionImageAttachedFileService;
    private final ResearchFieldService researchFieldService;
    private final ResearchFieldAttachedFileService researchFieldAttachedFileService;
    private final NoticeBoardService noticeBoardService;
    private final SeminarService seminarService;
    private final EventService eventService;
    private final SettingService settingService;

    public HomeController(AlbumService albumService, AlbumAttachedFileService albumAttachedFileService,
                          IntroductionService introductionService,
                          IntroductionImageService introductionImageService, IntroductionImageAttachedFileService introductionImageAttachedFileService,
                          ResearchFieldService researchFieldService, ResearchFieldAttachedFileService researchFieldAttachedFileService,
                          NoticeBoardService noticeBoardService,
                          SeminarService seminarService,
                          EventService eventService,
                          SettingService settingService) {
        this.albumService = albumService;
        this.albumAttachedFileService = albumAttachedFileService;
        this.introductionService = introductionService;
        this.introductionImageService = introductionImageService;
        this.introductionImageAttachedFileService = introductionImageAttachedFileService;
        this.researchFieldAttachedFileService = researchFieldAttachedFileService;
        this.researchFieldService = researchFieldService;
        this.noticeBoardService = noticeBoardService;
        this.seminarService = seminarService;
        this.eventService = eventService;
        this.settingService = settingService;
    }

    // Home
    @GetMapping("")
    public String home(Model model) {
        List<IntroductionImageDto> introductionImageDtoList = introductionImageService.findIntroductionImageListByActiveStatusIs();
        for (IntroductionImageDto introductionImageDto : introductionImageDtoList) {
            introductionImageAttachedFileService.findAttachedFileByIntroductionImageIdx(introductionImageDto.getIdx(), introductionImageDto);
        }

        List<ResearchFieldDto> researchFieldDtoList = researchFieldService.findAllByActiveStatusIsAndResearchFieldStatusIs();
        for (ResearchFieldDto researchFieldDto : researchFieldDtoList) {
            researchFieldAttachedFileService.findAttachedFileByResearchFieldIdx(researchFieldDto.getIdx(), researchFieldDto);
        }

        List<AlbumDto> albumDtoList = albumService.findAllByActiveStatusIsAndMainPagePriorityGreaterThanEqualOrderByMainPagePriorityAsc();
        for (AlbumDto albumDto : albumDtoList) {
            albumAttachedFileService.findAttachedFileByAlbumIdx(albumDto.getIdx(), albumDto);
        }

        model.addAttribute("introductionDto", introductionService.findIntroductionByActiveStatusIs());
        model.addAttribute("introductionImageDtoList", introductionImageDtoList);
        model.addAttribute("researchFieldDtoList", researchFieldDtoList);
        model.addAttribute("albumDtoList", albumDtoList);
        model.addAttribute("noticeBoardDtoList", noticeBoardService.findTop6ByOrderByIdxDesc());
        model.addAttribute("seminarDtoList", seminarService.findTop6ByOrderByPresentationDateDesc());
        model.addAttribute("eventDtoList", eventService.findTop4ByOrderByCurrentDate());
        model.addAttribute("settingDto", settingService.findSettingByIdx(1L));

        return "main/home";
    }
}