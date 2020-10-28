package kr.ac.univ.learnMore.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.learnMore.domain.LearnMoreAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static kr.ac.univ.learnMore.domain.QLearnMoreAttachedFile.learnMoreAttachedFile;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class LearnMoreAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public LearnMoreAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(LearnMoreAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<LearnMoreAttachedFile> findAttachedFileByLearnMoreIdx(Long learnMoreIdx) {
        /* SELECT *
         *   FROM AttachedFile
         *  WHERE learnMoreIdx = 'learnMoreIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(learnMoreAttachedFile)
                .where(learnMoreAttachedFile.learnMoreIdx.eq(learnMoreIdx))
                .orderBy(learnMoreAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileByLearnMoreIdx(Long learnMoreIdx) {
        /* DELETE FROM AttachedFile
         *  WHERE learnMoreIdx = 'learnMoreIdx'
         */
        return queryFactory
                .delete(learnMoreAttachedFile)
                .where(learnMoreAttachedFile.learnMoreIdx.eq(learnMoreIdx))
                .execute();
    }
}