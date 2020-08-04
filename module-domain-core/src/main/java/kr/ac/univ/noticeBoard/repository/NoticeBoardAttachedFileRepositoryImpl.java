package kr.ac.univ.noticeBoard.repository;

import java.util.List;

import javax.transaction.Transactional;

import kr.ac.univ.noticeBoard.domain.NoticeBoardAttachedFile;
import kr.ac.univ.noticeBoard.domain.QNoticeBoardAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional
public class NoticeBoardAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public NoticeBoardAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(NoticeBoardAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<NoticeBoardAttachedFile> findAttachedFileByNoticeBoardIdx(Long noticeBoardIdx) {
        QNoticeBoardAttachedFile noticeBoardAttachedFile = QNoticeBoardAttachedFile.noticeBoardAttachedFile;

        /* SELECT *
         *   FROM AttachedFile
         *  WHERE noticeBoardIdx = 'noticeBoardIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(noticeBoardAttachedFile)
                .where(noticeBoardAttachedFile.noticeBoardIdx.eq(noticeBoardIdx))
                .orderBy(noticeBoardAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileByNoticeBoardIdx(Long noticeBoardIdx) {
        QNoticeBoardAttachedFile noticeBoardAttachedFile = QNoticeBoardAttachedFile.noticeBoardAttachedFile;

        /* DELETE FROM AttachedFile
         *  WHERE noticeBoardIdx = 'noticeBoardIdx'
         */
        return queryFactory
                .delete(noticeBoardAttachedFile)
                .where(noticeBoardAttachedFile.noticeBoardIdx.eq(noticeBoardIdx))
                .execute();
    }
}