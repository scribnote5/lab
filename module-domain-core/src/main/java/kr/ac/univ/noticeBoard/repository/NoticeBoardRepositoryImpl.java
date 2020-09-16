package kr.ac.univ.noticeBoard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.noticeBoard.domain.QNoticeBoard;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class NoticeBoardRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public NoticeBoardRepositoryImpl(JPAQueryFactory queryFactory ) {
        super(NoticeBoard.class);
        this.queryFactory = queryFactory;
    }

    public List<NoticeBoard> findByTitle(String title) {
        QNoticeBoard noticeBoard = QNoticeBoard.noticeBoard;
        /*
         * SELECT *
         *   FROM notice_board
         *  WHERE title = 'title'
         */
        return queryFactory
                .selectFrom(noticeBoard)
                .where(noticeBoard.title.eq(title))
                .fetch();
    }

    public long updateViewsByIdx(Long idx) {
        QNoticeBoard noticeBoard = QNoticeBoard.noticeBoard;
        /*
         * UPDATE notice_board
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(noticeBoard)
                .set(noticeBoard.views, noticeBoard.views.add(1))
                .where(noticeBoard.idx.eq(idx))
                .execute();
    }

}