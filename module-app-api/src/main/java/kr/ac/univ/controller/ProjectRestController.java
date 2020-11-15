package kr.ac.univ.controller;

import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.project.dto.ProjectDto;
import kr.ac.univ.project.service.ProjectAttachedFileService;
import kr.ac.univ.project.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {
    private final ProjectService projectService;
    private final ProjectAttachedFileService projectAttachedFileService;

    public ProjectRestController(ProjectService projectService, ProjectAttachedFileService projectAttachedFileService) {
        this.projectService = projectService;
        this.projectAttachedFileService = projectAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postProject(@RequestBody @Valid ProjectDto projectDto) {
        System.out.println(projectDto);
        Long idx = projectService.insertProject(projectDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putProject(@PathVariable("idx") Long idx, @RequestBody @Valid ProjectDto projectDto) {
        projectService.updateProject(idx, projectDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteProject(@PathVariable("idx") Long idx) throws Exception {
        projectService.deleteProjectByIdx(idx);
        projectAttachedFileService.deleteAllAttachedFile(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    // 첨부 파일 업로드
    @PostMapping("/attachedFile")
    public ResponseEntity<?> uploadAttachedFile(Long idx, String createdBy, MultipartFile[] files) throws Exception {
        String fileValidationResult = FileValidator.isFileValid(files);

        // 파일 mime type 검사
        if (!"valid".equals(fileValidationResult)) {
            throw new FileTypeException(fileValidationResult);
        }

        projectAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        projectAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}