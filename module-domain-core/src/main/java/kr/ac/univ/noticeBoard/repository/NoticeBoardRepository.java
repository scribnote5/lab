package kr.ac.univ.noticeBoard.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
    Page<NoticeBoard> findAllByTitleContaining(Pageable pageable, String title);

    Page<NoticeBoard> findAllByContentContaining(Pageable pageable, String content);

    Page<NoticeBoard> findAllByCreatedByContaining(Pageable pageable, String username);



    Page<NoticeBoard> findAllByActiveStatusIs(Pageable pageable, ActiveStatus activeStatus);

    Page<NoticeBoard> findAllByTitleContainingAndActiveStatusIs(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<NoticeBoard> findAllByContentContainingAndActiveStatusIs(Pageable pageable, String content, ActiveStatus activeStatus);

    Page<NoticeBoard> findAllByCreatedByContainingAndActiveStatusIs(Pageable pageable, String username, ActiveStatus activeStatus);
}