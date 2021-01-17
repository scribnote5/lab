package kr.ac.univ.alumniAssociation.repository;

import kr.ac.univ.alumniAssociation.domain.AlumniAssociation;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumniAssociationRepository extends JpaRepository<AlumniAssociation, Long> {
    Page<AlumniAssociation> findAllByTitleContaining(Pageable pageable, String title);

    Page<AlumniAssociation> findAllByContentContaining(Pageable pageable, String content);

    Page<AlumniAssociation> findAllByCreatedByContaining(Pageable pageable, String username);


    Page<AlumniAssociation> findAllByActiveStatusIs(Pageable pageable, ActiveStatus activeStatus);

    Page<AlumniAssociation> findAllByTitleContainingAndActiveStatusIs(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<AlumniAssociation> findAllByContentContainingAndActiveStatusIs(Pageable pageable, String content, ActiveStatus activeStatus);

    Page<AlumniAssociation> findAllByCreatedByContainingAndActiveStatusIs(Pageable pageable, String username, ActiveStatus activeStatus);

    List<AlumniAssociation> findAllByActiveStatusIsAndMainPagePriorityLessThanEqualOrderByMainPagePriorityAsc(ActiveStatus activeStatus, Long mainPagePriority);

    Page<AlumniAssociation> findAllByOrderByMainPagePriorityAsc(Pageable pageable);

    Page<AlumniAssociation> findAllByActiveStatusIsOrderByMainPagePriorityAsc(Pageable pageable, ActiveStatus activeStatus);
}