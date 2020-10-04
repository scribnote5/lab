package kr.ac.univ.project.repository;

import kr.ac.univ.project.domain.ProjectAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAttachedFileRepository extends JpaRepository<ProjectAttachedFile, Long> {

}