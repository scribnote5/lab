package kr.ac.univ.researchField.repository;

import kr.ac.univ.researchField.domain.ResearchFieldAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchFieldAttachedFileRepository extends JpaRepository<ResearchFieldAttachedFile, Long> {

}