package kr.ac.univ.aboutUs.repository;

import kr.ac.univ.aboutUs.domain.AboutUs;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs, Long> {
    Page<AboutUs> findAllByTitleContaining(Pageable pageable, String title);

    Page<AboutUs> findAllByContentContaining(Pageable pageable, String content);

    Page<AboutUs> findAllByCreatedByContaining(Pageable pageable, String username);

    AboutUs findAllByActiveStatusIs(ActiveStatus activeStatus);

    Long countAllByActiveStatus(ActiveStatus activeStatus);
}