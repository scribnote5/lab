package kr.ac.univ.seminar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.seminar.domain.Seminar;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static kr.ac.univ.seminar.domain.QSeminar.seminar;

@Repository
@Transactional
public class SeminarRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public SeminarRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Seminar.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {

        /*
         * UPDATE notice_board
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(seminar)
                .set(seminar.views, seminar.views.add(1))
                .where(seminar.idx.eq(idx))
                .execute();
    }

}