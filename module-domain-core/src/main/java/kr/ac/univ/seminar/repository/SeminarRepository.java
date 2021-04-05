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
    Page<Seminar> findAllByOrderByPresentationDateDesc(Pageable pageable);

    Page<Seminar> findAllByTitleContainingOrderByPresentationDateDesc(Pageable pageable, String title);

    Page<Seminar> findAllByContentContainingOrderByPresentationDateDesc(Pageable pageable, String presenter);

    Page<Seminar> findAllByCreatedByContainingOrderByPresentationDateDesc(Pageable pageable, String username);

    Page<Seminar> findAllByActiveStatusIsOrderByPresentationDateDesc(Pageable pageable, ActiveStatus activeStatus);

    Page<Seminar> findAllByTitleContainingAndActiveStatusIsOrderByPresentationDateDesc(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<Seminar> findAllByContentContainingAndActiveStatusIsOrderByPresentationDateDesc(Pageable pageable, String presenter, ActiveStatus activeStatus);

    Page<Seminar> findAllByCreatedByContainingAndActiveStatusIsOrderByPresentationDateDesc(Pageable pageable, String username, ActiveStatus activeStatus);

    List<Seminar> findTop6ByOrderByPresentationDateDesc();
}