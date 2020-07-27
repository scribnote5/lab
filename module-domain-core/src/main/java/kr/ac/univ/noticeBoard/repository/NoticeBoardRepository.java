package kr.ac.univ.noticeBoard.repository;

import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

}