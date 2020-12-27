package kr.ac.univ.learnMoreRead.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.learnMoreRead.domain.LearnMoreRead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearnMoreReadRepository extends JpaRepository<LearnMoreRead, Long> {
    Page<LearnMoreRead> findAllByTitleContaining(Pageable pageable, String title);

    Page<LearnMoreRead> findAllByCreatedByContaining(Pageable pageable, String username);

    List<LearnMoreRead> findAllByActiveStatusIs(ActiveStatus activeStatus);

    Long countAllByActiveStatus(ActiveStatus activeStatus);
}