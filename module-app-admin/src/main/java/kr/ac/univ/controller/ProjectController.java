package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.project.dto.ProjectDto;
import kr.ac.univ.project.service.ProjectAttachedFileService;
import kr.ac.univ.project.service.ProjectService;
import kr.ac.univ.researchField.service.ResearchFieldService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectAttachedFileService projectAttachedFileService;
    private final ResearchFieldService researchFieldService;

    public ProjectController(ProjectService projectService, ProjectAttachedFileService projectAttachedFileService, ResearchFieldService researchFieldService) {
        this.projectService = projectService;
        this.projectAttachedFileService = projectAttachedFileService;
        this.researchFieldService = researchFieldService;
    }

    // List
    @GetMapping("/list")
    public String projectList(Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("projectDtoList", projectService.findProjectList(pageable, searchDto));

        return "project/list";
    }

    // Form
    @GetMapping("/form{idx}")
    public String projectForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        ProjectDto projectDto = projectService.findProjectByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (projectDto.isAccess()) {
            projectDto = projectAttachedFileService.findAttachedFileByProjectIdx(idx, projectDto);

            model.addAttribute("projectDto", projectDto);
            model.addAttribute("researchFieldDto", researchFieldService.findResearchFieldByIdxInActive(projectDto.getResearchFieldIdx()));
            model.addAttribute("researchFieldDtoList", researchFieldService.findResearchFieldListByActiveStatusIs());

            returnPage = "project/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String projectRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        ProjectDto projectDto = null;

        projectDto = projectService.findProjectByIdx(idx);
        projectDto = projectAttachedFileService.findAttachedFileByProjectIdx(idx, projectDto);

        model.addAttribute("projectDto", projectDto);
        model.addAttribute("researchFieldDto", researchFieldService.findResearchFieldByIdxInActive(projectDto.getResearchFieldIdx()));

        return "project/read";
    }
}