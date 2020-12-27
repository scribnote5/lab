package kr.ac.univ.learnMoreVideo.repository;


import kr.ac.univ.learnMoreVideo.domain.LearnMoreVideoAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnMoreVideoAttachedFileRepository extends JpaRepository<LearnMoreVideoAttachedFile, Long> {

}