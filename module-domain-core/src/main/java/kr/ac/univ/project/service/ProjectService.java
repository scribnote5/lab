package kr.ac.univ.project.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.project.domain.Project;
import kr.ac.univ.project.dto.ProjectDto;
import kr.ac.univ.project.dto.mapper.ProjectMapper;
import kr.ac.univ.project.repository.ProjectRepository;
import kr.ac.univ.project.repository.ProjectRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProjectService {
    @Value("${module.name}")
    private String moduleName;
    private final ProjectRepository projectRepository;
    private final ProjectRepositoryImpl projectRepositoryImpl;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectRepositoryImpl projectRepositoryImpl, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectRepositoryImpl = projectRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<ProjectDto> findProjectList(Pageable pageable, SearchDto searchDto) {
        Page<Project> projectList = null;
        Page<ProjectDto> projectDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                if ("module-app-admin".equals(moduleName)) {
                    projectList = projectRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    projectList = projectRepository.findAllByTitleContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    projectList = null;
                }
                break;
            case "CONTENT":
                if ("module-app-admin".equals(moduleName)) {
                    projectList = projectRepository.findAllByContentContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    projectList = projectRepository.findAllByContentContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    projectList = null;
                }
                break;
            case "ID":
                if ("module-app-admin".equals(moduleName)) {
                    projectList = projectRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    projectList = projectRepository.findAllByCreatedByContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    projectList = null;
                }
                break;
            default:
                if ("module-app-admin".equals(moduleName)) {
                    projectList = projectRepository.findAll(pageable);
                } else if ("module-app-web".equals(moduleName)) {
                    projectList = projectRepository.findAllByActiveStatusIs(pageable, ActiveStatus.ACTIVE);
                } else {
                    projectList = null;
                }
                break;
        }

        projectDtoList = new PageImpl<ProjectDto>(ProjectMapper.INSTANCE.toDto(projectList.getContent()), pageable, projectList.getTotalElements());

        // NewIcon 판별
        for (ProjectDto projectDto : projectDtoList) {
            projectDto.setNewIcon(NewIconCheck.isNew(projectDto.getCreatedDate()));
        }

        return projectDtoList;
    }

    public Long insertProject(ProjectDto projectDto) {

        return projectRepository.save(ProjectMapper.INSTANCE.toEntity(projectDto)).getIdx();
    }

    public ProjectDto findProjectByIdx(Long idx) {
        ProjectDto projectDto = ProjectMapper.INSTANCE.toDto(projectRepository.findById(idx).orElse(new Project()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            projectDto.setAccess(true);
        }
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        else if (AccessCheck.isAccessInModuleWeb(projectDto.getCreatedBy())) {
            projectDto.setAccess(true);
        } else {
            projectDto.setAccess(false);
        }

        projectRepositoryImpl.updateViewsByIdx(idx);

        return projectDto;
    }

    @Transactional
    public Long updateProject(Long idx, ProjectDto projectDto) {
        Project persistProject = projectRepository.getOne(idx);
        Project project = ProjectMapper.INSTANCE.toEntity(projectDto);

        persistProject.update(project);

        return projectRepository.save(project).getIdx();
    }

    public void deleteProjectByIdx(Long idx) {
        projectRepository.deleteById(idx);
    }
}