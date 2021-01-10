package kr.ac.univ.learnMoreRead.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.learnMoreRead.domain.LearnMoreRead;
import kr.ac.univ.learnMoreRead.domain.QLearnMoreRead;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class LearnMoreReadRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public LearnMoreReadRepositoryImpl(JPAQueryFactory queryFactory) {
        super(LearnMoreRead.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QLearnMoreRead learnMoreRead = QLearnMoreRead.learnMoreRead;
        /*
         * UPDATE learnMoreRead
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(learnMoreRead)
                .set(learnMoreRead.views, learnMoreRead.views.add(1))
                .where(learnMoreRead.idx.eq(idx))
                .execute();
    }

}