package kr.ac.univ.noticeBoard.repository;

import kr.ac.univ.noticeBoard.domain.NoticeBoardAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardAttachedFileRepository extends JpaRepository<NoticeBoardAttachedFile, Long> {

}