package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.maintenance.dto.MaintenanceCommentDto;
import kr.ac.univ.maintenance.dto.MaintenanceDto;
import kr.ac.univ.maintenance.service.MaintenanceAttachedFileService;
import kr.ac.univ.maintenance.service.MaintenanceCommentService;
import kr.ac.univ.maintenance.service.MaintenanceService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;
    private final MaintenanceAttachedFileService maintenanceAttachedFileService;
    private final MaintenanceCommentService maintenanceCommentService;

    public MaintenanceController(MaintenanceService maintenanceService, MaintenanceAttachedFileService maintenanceAttachedFileService, MaintenanceCommentService maintenanceCommentService) {
        this.maintenanceService = maintenanceService;
        this.maintenanceAttachedFileService = maintenanceAttachedFileService;
        this.maintenanceCommentService = maintenanceCommentService;
    }

    // List
    @GetMapping("/list")
    public String maintenanceList(Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("maintenanceDtoList", maintenanceService.findMaintenanceList(pageable, searchDto));

        return "maintenance/list";
    }

    // Form
    @GetMapping("/form{idx}")
    public String maintenanceForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        MaintenanceDto maintenanceDto = maintenanceService.findMaintenanceByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (maintenanceDto.isAccess()) {
            maintenanceDto = maintenanceAttachedFileService.findAttachedFileByMaintenanceIdx(idx, maintenanceDto);

            model.addAttribute("maintenanceDto", maintenanceDto);

            returnPage = "maintenance/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String maintenanceRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        MaintenanceDto maintenanceDto = null;
        List<MaintenanceCommentDto> maintenanceCommentDtoList = null;

        maintenanceDto = maintenanceService.findMaintenanceByIdx(idx);
        maintenanceDto = maintenanceAttachedFileService.findAttachedFileByMaintenanceIdx(idx, maintenanceDto);
        maintenanceCommentDtoList = maintenanceCommentService.findAllByMaintenanceIdxOrderByCreatedDateDesc(idx);

        model.addAttribute("maintenanceDto", maintenanceDto);
        model.addAttribute("maintenanceCommentDtoList", maintenanceCommentDtoList);

        return "maintenance/read";
    }
}