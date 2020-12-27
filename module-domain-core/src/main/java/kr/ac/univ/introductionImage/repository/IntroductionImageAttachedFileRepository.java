package kr.ac.univ.introductionImage.repository;

import kr.ac.univ.introductionImage.domain.IntroductionImageAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroductionImageAttachedFileRepository extends JpaRepository<IntroductionImageAttachedFile, Long> {

}