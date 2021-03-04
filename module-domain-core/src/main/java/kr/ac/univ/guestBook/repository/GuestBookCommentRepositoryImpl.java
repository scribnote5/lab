package kr.ac.univ.guestBook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.guestBook.domain.GuestBookComment;
import kr.ac.univ.guestBook.domain.QGuestBookComment;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class GuestBookCommentRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public GuestBookCommentRepositoryImpl(JPAQueryFactory queryFactory) {
        super(GuestBookComment.class);
        this.queryFactory = queryFactory;
    }

    public Long deleteAllByGuestBookIdx(Long guestBookIdx) {
        QGuestBookComment guestBookComment = QGuestBookComment.guestBookComment;

        /*
         * DELETE FROM AttachedFile
         * WHERE guestBookIdx = 'guestBookIdx'
         */
        return queryFactory
                .delete(guestBookComment)
                .where(guestBookComment.guestBookIdx.eq(guestBookIdx))
                .execute();
    }
}