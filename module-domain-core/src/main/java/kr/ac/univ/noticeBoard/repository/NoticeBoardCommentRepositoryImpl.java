package kr.ac.univ.noticeBoard.repository;

import javax.transaction.Transactional;

import kr.ac.univ.noticeBoard.domain.NoticeBoardComment;
import kr.ac.univ.noticeBoard.domain.QNoticeBoardComment;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional
public class NoticeBoardCommentRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public NoticeBoardCommentRepositoryImpl(JPAQueryFactory queryFactory) {
        super(NoticeBoardComment.class);
        this.queryFactory = queryFactory;
    }

    public Long deleteAllByNoticeBoardIdx(Long noticeBoardIdx) {
        QNoticeBoardComment noticeBoardComment = QNoticeBoardComment.noticeBoardComment;

        /*
         * DELETE FROM AttachedFile
         * WHERE noticeBoardIdx = 'noticeBoardIdx'
         */
        return queryFactory
                .delete(noticeBoardComment)
                .where(noticeBoardComment.noticeBoardIdx.eq(noticeBoardIdx))
                .execute();
    }
}