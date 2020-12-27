package kr.ac.univ.learnMoreRead.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.learnMoreRead.domain.LearnMoreReadAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static kr.ac.univ.learnMoreRead.domain.QLearnMoreReadAttachedFile.learnMoreReadAttachedFile;

@Repository
@Transactional
public class LearnMoreReadAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public LearnMoreReadAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(LearnMoreReadAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<LearnMoreReadAttachedFile> findAttachedFileByLearnMoreReadIdx(Long learnMoreReadIdx) {
        /* SELECT *
         *   FROM AttachedFile
         *  WHERE learnMoreReadIdx = 'learnMoreReadIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(learnMoreReadAttachedFile)
                .where(learnMoreReadAttachedFile.learnMoreReadIdx.eq(learnMoreReadIdx))
                .orderBy(learnMoreReadAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileByLearnMoreReadIdx(Long learnMoreReadIdx) {
        /* DELETE FROM AttachedFile
         *  WHERE learnMoreReadIdx = 'learnMoreReadIdx'
         */
        return queryFactory
                .delete(learnMoreReadAttachedFile)
                .where(learnMoreReadAttachedFile.learnMoreReadIdx.eq(learnMoreReadIdx))
                .execute();
    }
}