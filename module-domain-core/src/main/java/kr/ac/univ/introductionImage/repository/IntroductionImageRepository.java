package kr.ac.univ.introductionImage.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.introductionImage.domain.IntroductionImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntroductionImageRepository extends JpaRepository<IntroductionImage, Long> {
    Page<IntroductionImage> findAllByTitleContaining(Pageable pageable, String title);

    Page<IntroductionImage> findAllByCreatedByContaining(Pageable pageable, String username);

    Page<IntroductionImage> findAllByActiveStatusIs(Pageable pageable, ActiveStatus activeStatus);

    Page<IntroductionImage> findAllByTitleContainingAndActiveStatusIs(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<IntroductionImage> findAllByCreatedByContainingAndActiveStatusIs(Pageable pageable, String username, ActiveStatus activeStatus);

    List<IntroductionImage> findAllByActiveStatusIsAndMainPagePriorityGreaterThanEqualOrderByMainPagePriorityAsc(ActiveStatus activeStatus, Long mainPagePriority);

    IntroductionImage findByMainPagePriorityIsAndActiveStatusIs(Long mainPagePriority, ActiveStatus activeStatus);
}