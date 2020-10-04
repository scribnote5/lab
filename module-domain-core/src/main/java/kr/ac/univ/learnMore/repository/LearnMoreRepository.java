package kr.ac.univ.learnMore.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.learnMore.domain.LearnMore;
import kr.ac.univ.learnMore.domain.enums.DownloadFileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnMoreRepository extends JpaRepository<LearnMore, Long> {
    Page<LearnMore> findAllByTitleContaining(Pageable pageable, String title);

    Page<LearnMore> findAllByCreatedByContaining(Pageable pageable, String username);

    Page<LearnMore> findAllByDownloadFileTypeIs(Pageable pageable, DownloadFileType downloadFileType);
}