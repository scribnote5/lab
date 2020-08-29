package kr.ac.univ.noticeBoard.repository;

import java.util.List;

import kr.ac.univ.noticeBoard.domain.NoticeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardCommentRepository extends JpaRepository<NoticeBoardComment, Long> {
    List<NoticeBoardComment> findAllByNoticeBoardIdxOrderByCreatedDateDesc(Long noticeBoardIdx);

    void deleteAllByNoticeBoardIdx(Long noticeBoardIdx);
}