package kr.ac.univ.introduction.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.introduction.domain.Introduction;
import kr.ac.univ.introduction.domain.QIntroduction;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class IntroductionRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public IntroductionRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Introduction.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QIntroduction introduction = QIntroduction.introduction;
        /*
         * UPDATE introduction
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(introduction)
                .set(introduction.views, introduction.views.add(1))
                .where(introduction.idx.eq(idx))
                .execute();
    }

}