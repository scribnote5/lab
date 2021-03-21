package kr.ac.univ.event.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.event.domain.Event;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static kr.ac.univ.event.domain.QEvent.event;

@Repository
@Transactional
public class EventRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public EventRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Event.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        /*
         * UPDATE event
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(event)
                .set(event.views, event.views.add(1))
                .where(event.idx.eq(idx))
                .execute();
    }
}