package kr.ac.univ.controller;

import kr.ac.univ.project.domain.enums.ProjectStatus;
import kr.ac.univ.project.dto.ProjectDto;
import kr.ac.univ.project.service.ProjectAttachedFileService;
import kr.ac.univ.project.service.ProjectService;
import kr.ac.univ.researchField.service.ResearchFieldService;
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
    public String projectList(Model model) {
        model.addAttribute("currentProjectDtoList", projectService.findAllByProjectStatusIsAndActiveStatusIs(ProjectStatus.CURRENT));
        model.addAttribute("previousProjectDtoList", projectService.findAllByProjectStatusIsAndActiveStatusIs(ProjectStatus.PREVIOUS));

        return "project/list";
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