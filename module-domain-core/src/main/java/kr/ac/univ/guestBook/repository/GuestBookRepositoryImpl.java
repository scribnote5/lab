package kr.ac.univ.guestBook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.guestBook.domain.GuestBook;
import kr.ac.univ.guestBook.domain.QGuestBook;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GuestBookRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public GuestBookRepositoryImpl(JPAQueryFactory queryFactory ) {
        super(GuestBook.class);
        this.queryFactory = queryFactory;
    }

    public List<GuestBook> findByTitle(String title) {
        QGuestBook guestBook = QGuestBook.guestBook;
        /*
         * SELECT *
         *   FROM notice_board
         *  WHERE title = 'title'
         */
        return queryFactory
                .selectFrom(guestBook)
                .where(guestBook.title.eq(title))
                .fetch();
    }

    public long updateViewsByIdx(Long idx) {
        QGuestBook guestBook = QGuestBook.guestBook;
        /*
         * UPDATE notice_board
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(guestBook)
                .set(guestBook.views, guestBook.views.add(1))
                .where(guestBook.idx.eq(idx))
                .execute();
    }

}