package kr.ac.univ.project.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.project.domain.Project;
import kr.ac.univ.project.domain.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findAllByTitleContaining(Pageable pageable, String title);

    Page<Project> findAllByContentContaining(Pageable pageable, String content);

    Page<Project> findAllByCreatedByContaining(Pageable pageable, String username);

    List<Project> findAllByProjectStatusIsAndActiveStatusIsOrderByStartDateDesc(ProjectStatus projectStatus, ActiveStatus activeStatus);

    Long countAllByActiveStatusIsAndProjectStatus(ActiveStatus activeStatus, ProjectStatus projectStatus);
}