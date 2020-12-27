package kr.ac.univ.learnMoreVideo.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.learnMoreVideo.domain.LearnMoreVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearnMoreVideoRepository extends JpaRepository<LearnMoreVideo, Long> {
    Page<LearnMoreVideo> findAllByTitleContaining(Pageable pageable, String title);

    Page<LearnMoreVideo> findAllByCreatedByContaining(Pageable pageable, String username);

    List<LearnMoreVideo> findAllByActiveStatusIs(ActiveStatus activeStatus);

    Long countAllByActiveStatus(ActiveStatus activeStatus);
}