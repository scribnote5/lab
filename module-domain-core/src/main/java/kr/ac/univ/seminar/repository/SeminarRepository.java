package kr.ac.univ.seminar.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.seminar.domain.Seminar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Long> {
    Page<Seminar> findAllByTitleContaining(Pageable pageable, String title);

    Page<Seminar> findAllByPresenterContaining(Pageable pageable, String presenter);


    Page<Seminar> findAllByActiveStatusIs(Pageable pageable, ActiveStatus activeStatus);

    Page<Seminar> findAllByTitleContainingAndActiveStatusIs(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<Seminar> findAllByPresenterContainingAndActiveStatusIs(Pageable pageable, String presenter, ActiveStatus activeStatus);

    Page<Seminar> findAllByCreatedByContainingAndActiveStatusIs(Pageable pageable, String username, ActiveStatus activeStatus);
}