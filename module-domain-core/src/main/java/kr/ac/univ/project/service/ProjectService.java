package kr.ac.univ.project.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.project.domain.Project;
import kr.ac.univ.project.domain.enums.ProjectStatus;
import kr.ac.univ.project.dto.ProjectDto;
import kr.ac.univ.project.dto.mapper.ProjectMapper;
import kr.ac.univ.project.repository.ProjectRepository;
import kr.ac.univ.project.repository.ProjectRepositoryImpl;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.EmptyUtil;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
                projectList = projectRepository.findAllByTitleContainingOrderByStartDateDesc(pageable, searchDto.getKeyword());
                break;
            case "CONTENT":
                projectList = projectRepository.findAllByContentContainingOrderByStartDateDesc(pageable, searchDto.getKeyword());
                break;
            case "ID":
                projectList = projectRepository.findAllByCreatedByContainingOrderByStartDateDesc(pageable, searchDto.getKeyword());
                break;
            default:
                projectList = projectRepository.findAllByOrderByStartDateDesc(pageable);
                break;
        }

        projectDtoList = new PageImpl<>(ProjectMapper.INSTANCE.toDto(projectList.getContent()), pageable, projectList.getTotalElements());

        // NewIcon 판별
        for (ProjectDto projectDto : projectDtoList) {
            projectDto.setNewIcon(NewIconCheck.isNew(projectDto.getCreatedDate()));
        }

        return projectDtoList;
    }

    public List<ProjectDto> findAllByProjectStatusIsAndActiveStatusIs(ProjectStatus projectStatus) {
        List<ProjectDto> projectDtoList = ProjectMapper.INSTANCE.toDto(projectRepository.findAllByProjectStatusIsAndActiveStatusIsOrderByStartDateDesc(projectStatus, ActiveStatus.ACTIVE));

        // NewIcon 판별
        for (ProjectDto projectDto : projectDtoList) {
            projectDto.setNewIcon(NewIconCheck.isNew(projectDto.getCreatedDate()));
        }

        return projectDtoList;
    }

    public Long countAllByActiveStatusIsAndProjectStatus() {
        return projectRepository.countAllByActiveStatusIsAndProjectStatusOrderByStartDateDesc(ActiveStatus.ACTIVE, ProjectStatus.CURRENT);
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
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        // 탈퇴 회원은 권한을 general로 설정 후 권한을 검사함
        else {
            User user = userRepository.findByUsername(projectDto.getCreatedBy());

            projectDto.setAccess(AccessCheck.isAccessInGeneral(projectDto.getCreatedBy(), EmptyUtil.isEmpty(user) ? "general" : user.getAuthorityType().getAuthorityType()));
        }

        projectRepositoryImpl.updateViewsByIdx(idx);
        projectDto.setViews(projectDto.getViews() + 1);

        return projectDto;
    }

    @Transactional
    public Long updateProject(Long idx, ProjectDto projectDto) {
        Project persistProject = projectRepository.getOne(idx);
        Project project = ProjectMapper.INSTANCE.toEntity(projectDto);

        persistProject.update(project);

        return projectRepository.save(persistProject).getIdx();
    }

    public void deleteProjectByIdx(Long idx) {
        projectRepository.deleteById(idx);
    }
}