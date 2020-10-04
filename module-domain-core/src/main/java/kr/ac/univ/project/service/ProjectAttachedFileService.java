package kr.ac.univ.project.service;

import kr.ac.univ.project.domain.ProjectAttachedFile;
import kr.ac.univ.project.dto.ProjectDto;
import kr.ac.univ.project.dto.mapper.ProjectMapper;
import kr.ac.univ.project.repository.ProjectAttachedFileRepository;
import kr.ac.univ.project.repository.ProjectAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class ProjectAttachedFileService {
    private final ProjectAttachedFileRepository projectAttachedFileRepository;
    private final ProjectAttachedFileRepositoryImpl projectAttachedFileRepositoryImpl;

    public ProjectAttachedFileService(ProjectAttachedFileRepository projectAttachedFileRepository, ProjectAttachedFileRepositoryImpl projectAttachedFileRepositoryImpl) {
        this.projectAttachedFileRepository = projectAttachedFileRepository;
        this.projectAttachedFileRepositoryImpl = projectAttachedFileRepositoryImpl;
    }

    public ProjectDto findAttachedFileByProjectIdx(Long projectIdx, ProjectDto projectDto) {

        return ProjectMapper.INSTANCE.toDto(projectDto, projectAttachedFileRepositoryImpl.findAttachedFileByProjectIdx(projectIdx));
    }

    public void insertAttachedFile(ProjectAttachedFile attachedFile) {
        projectAttachedFileRepository.save(attachedFile);
    }

    public ProjectAttachedFile findAttachedFileByIdx(Long idx) {
        return projectAttachedFileRepository.findById(idx).orElse(new ProjectAttachedFile());
    }

    public ProjectAttachedFile getAttachedFileByIdx(Long idx) {
        return projectAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        projectAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByProjectIdx(Long idx) {
        return projectAttachedFileRepositoryImpl.deleteAttachedFileByProjectIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param projectIdx
     * @param files
     */
    public void uploadAttachedFile(Long projectIdx, String createdBy, MultipartFile[] files) throws Exception {
        ProjectAttachedFile uploadFile = new ProjectAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);

            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = ProjectAttachedFile.builder()
                    .projectIdx(projectIdx)
                    .createdBy(createdBy)
                    .fileName(file.getOriginalFilename())
                    .savedFileName(savedFileName)
                    .fileSize(FileUtil.convertFileSize(file.getSize()))
                    .build();

            insertAttachedFile(uploadFile);
        }
    }

    /**
     * 첨부 파일 삭제
     *
     * @param deleteAttachedFileIdxList
     */
    public void deleteAttachedFile(List<Long> deleteAttachedFileIdxList) throws Exception {
        for (Long idx : deleteAttachedFileIdxList) {
            ProjectAttachedFile attachedFile = findAttachedFileByIdx(idx);

            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

            deleteAttachedFileByIdx(attachedFile.getIdx());
        }
    }

    /**
     * 모든 첨부 파일 삭제
     *
     * @param projectIdx
     */
    public void deleteAllAttachedFile(Long projectIdx) throws Exception {
        List<ProjectAttachedFile> attachedFileList = projectAttachedFileRepositoryImpl.findAttachedFileByProjectIdx(projectIdx);

        for (ProjectAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByProjectIdx(projectIdx);
    }
}