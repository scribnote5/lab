package kr.ac.univ.controller;

import kr.ac.univ.aboutUs.dto.AboutUsDto;
import kr.ac.univ.aboutUs.service.AboutUsService;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.learnMoreRead.dto.LearnMoreReadDto;
import kr.ac.univ.learnMoreRead.service.LearnMoreReadAttachedFileService;
import kr.ac.univ.learnMoreRead.service.LearnMoreReadService;
import kr.ac.univ.learnMoreVideo.dto.LearnMoreVideoDto;
import kr.ac.univ.learnMoreVideo.service.LearnMoreVideoAttachedFileService;
import kr.ac.univ.learnMoreVideo.service.LearnMoreVideoService;
import kr.ac.univ.project.service.ProjectService;
import kr.ac.univ.publication.service.PublicationService;
import kr.ac.univ.setting.dto.SettingDto;
import kr.ac.univ.setting.service.SettingService;
import kr.ac.univ.user.domain.enums.UserStatus;
import kr.ac.univ.user.domain.enums.UserType;
import kr.ac.univ.user.service.UserService;
import kr.ac.univ.util.EmptyUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/about-us")
public class AboutUsController {
    private final AboutUsService aboutUsService;
    private final SettingService settingService;
    private final UserService userService;
    private final PublicationService publicationService;
    private final ProjectService projectService;
    private final LearnMoreReadService learnMoreReadService;
    private final LearnMoreReadAttachedFileService learnMoreReadAttachedFileService;
    private final LearnMoreVideoService learnMoreVideoService;
    private final LearnMoreVideoAttachedFileService learnMoreVideoAttachedFileService;

    public AboutUsController(AboutUsService aboutUsService,
                             SettingService settingService,
                             UserService userService,
                             PublicationService publicationService,
                             ProjectService projectService,
                             LearnMoreReadService learnMoreReadService, LearnMoreReadAttachedFileService learnMoreReadAttachedFileService,
                             LearnMoreVideoService learnMoreVideoService, LearnMoreVideoAttachedFileService learnMoreVideoAttachedFileService) {
        this.aboutUsService = aboutUsService;
        this.settingService = settingService;
        this.userService = userService;
        this.publicationService = publicationService;
        this.projectService = projectService;
        this.learnMoreReadService = learnMoreReadService;
        this.learnMoreReadAttachedFileService = learnMoreReadAttachedFileService;
        this.learnMoreVideoService = learnMoreVideoService;
        this.learnMoreVideoAttachedFileService = learnMoreVideoAttachedFileService;
    }

    // Read
    @GetMapping("/")
    public String aboutUsList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        AboutUsDto aboutUsDto = aboutUsService.findAboutUsByActiveStatusIs();
        SettingDto settingDto = settingService.findSettingByIdx(1L);

        List<LearnMoreReadDto> learnMoreReadDtoList = learnMoreReadService.findAllByActiveStatusIs();
        for (LearnMoreReadDto learnMoreReadDto : learnMoreReadDtoList) {
            learnMoreReadAttachedFileService.findAttachedFileByLearnMoreIdx(learnMoreReadDto.getIdx(), learnMoreReadDto);
        }

        List<LearnMoreVideoDto> learnMoreVideoDtoList = learnMoreVideoService.findAllByActiveStatusIs();
        for (LearnMoreVideoDto learnMoreVideoDto : learnMoreVideoDtoList) {
            learnMoreVideoAttachedFileService.findAttachedFileByLearnMoreIdx(learnMoreVideoDto.getIdx(), learnMoreVideoDto);
        }

        aboutUsDto = AboutUsDto.builder()
                .labStartDate(settingDto.getLabStartDate())
                .labMaintenanceYears(settingDto.getLabStartDate().getDayOfYear())
                .publicationCount(publicationService.countAllByActiveStatusIs())
                .projectCount(projectService.countAllByActiveStatusIsAndProjectStatus())
                .attendingPhdUserCount(userService.countAllByActiveStatusIsAndUserStatusIsAndUserTypeIs(UserType.FULL_TIME_PhD))
                .attendingMsUserCount(userService.countAllByActiveStatusIsAndUserStatusIsAndUserTypeIs(UserType.FULL_TIME_MS))
                .labMaintenanceYearsCountContent(settingDto.getLabMaintenanceYearsCountContent())
                .userCountContent(settingDto.getLabMaintenanceYearsCountContent())
                .publicationCountContent(settingDto.getPublicationCountContent())
                .projectCountContent(settingDto.getProjectCountContent())
                .build();

        model.addAttribute("aboutUsDto", aboutUsDto);
        model.addAttribute("learnMoreReadDtoList", learnMoreReadDtoList);
        model.addAttribute("learnMoreVideoDtoList", learnMoreVideoDtoList);

        return "aboutUs/read";
    }
}