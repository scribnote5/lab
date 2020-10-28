package kr.ac.univ.introduction.repository;

import kr.ac.univ.introduction.domain.Introduction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroductionRepository extends JpaRepository<Introduction, Long> {
    Page<Introduction> findAllByTitleContaining(Pageable pageable, String title);

    Page<Introduction> findAllByContentContaining(Pageable pageable, String content);

    Page<Introduction> findAllByCreatedByContaining(Pageable pageable, String username);
}