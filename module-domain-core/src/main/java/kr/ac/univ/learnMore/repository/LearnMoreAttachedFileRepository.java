package kr.ac.univ.learnMore.repository;


import kr.ac.univ.learnMore.domain.LearnMoreAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnMoreAttachedFileRepository extends JpaRepository<LearnMoreAttachedFile, Long> {

}