package kr.ac.univ.learnMoreRead.repository;


import kr.ac.univ.learnMoreRead.domain.LearnMoreReadAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnMoreReadAttachedFileRepository extends JpaRepository<LearnMoreReadAttachedFile, Long> {

}