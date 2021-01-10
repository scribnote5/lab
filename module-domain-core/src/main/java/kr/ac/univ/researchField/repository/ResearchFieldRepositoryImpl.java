package kr.ac.univ.researchField.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.researchField.domain.ResearchField;
import kr.ac.univ.researchField.domain.QResearchField;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class ResearchFieldRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public ResearchFieldRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ResearchField.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QResearchField researchField = QResearchField.researchField;
        /*
         * UPDATE researchField
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(researchField)
                .set(researchField.views, researchField.views.add(1))
                .where(researchField.idx.eq(idx))
                .execute();
    }
}