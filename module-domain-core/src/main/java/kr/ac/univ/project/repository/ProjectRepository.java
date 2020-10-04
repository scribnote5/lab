package kr.ac.univ.project.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findAllByTitleContaining(Pageable pageable, String title);

    Page<Project> findAllByContentContaining(Pageable pageable, String content);

    Page<Project> findAllByCreatedByContaining(Pageable pageable, String username);



    Page<Project> findAllByActiveStatusIs(Pageable pageable, ActiveStatus activeStatus);

    Page<Project> findAllByTitleContainingAndActiveStatusIs(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<Project> findAllByContentContainingAndActiveStatusIs(Pageable pageable, String content, ActiveStatus activeStatus);

    Page<Project> findAllByCreatedByContainingAndActiveStatusIs(Pageable pageable, String username, ActiveStatus activeStatus);
}