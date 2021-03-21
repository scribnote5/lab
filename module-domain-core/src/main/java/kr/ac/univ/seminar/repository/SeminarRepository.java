package kr.ac.univ.seminar.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.seminar.domain.Seminar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Long> {
    Page<Seminar> findAllByTitleContaining(Pageable pageable, String title);

    Page<Seminar> findAllByContentContaining(Pageable pageable, String presenter);

    Page<Seminar> findAllByCreatedByContaining(Pageable pageable, String username);

    Page<Seminar> findAllByActiveStatusIs(Pageable pageable, ActiveStatus activeStatus);

    Page<Seminar> findAllByTitleContainingAndActiveStatusIs(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<Seminar> findAllByContentContainingAndActiveStatusIs(Pageable pageable, String presenter, ActiveStatus activeStatus);

    Page<Seminar> findAllByCreatedByContainingAndActiveStatusIs(Pageable pageable, String username, ActiveStatus activeStatus);

    List<Seminar> findTop4ByOrderByIdxDesc();
}