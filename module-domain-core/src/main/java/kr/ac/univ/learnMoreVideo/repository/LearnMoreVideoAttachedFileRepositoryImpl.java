package kr.ac.univ.learnMoreVideo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.learnMoreVideo.domain.LearnMoreVideoAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static kr.ac.univ.learnMoreVideo.domain.QLearnMoreVideoAttachedFile.learnMoreVideoAttachedFile;

@Repository
@Transactional
public class LearnMoreVideoAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public LearnMoreVideoAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(LearnMoreVideoAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<LearnMoreVideoAttachedFile> findAttachedFileByLearnMoreVideoIdx(Long learnMoreVideoIdx) {
        /* SELECT *
         *   FROM AttachedFile
         *  WHERE learnMoreVideoIdx = 'learnMoreVideoIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(learnMoreVideoAttachedFile)
                .where(learnMoreVideoAttachedFile.learnMoreVideoIdx.eq(learnMoreVideoIdx))
                .orderBy(learnMoreVideoAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileByLearnMoreVideoIdx(Long learnMoreVideoIdx) {
        /* DELETE FROM AttachedFile
         *  WHERE learnMoreVideoIdx = 'learnMoreVideoIdx'
         */
        return queryFactory
                .delete(learnMoreVideoAttachedFile)
                .where(learnMoreVideoAttachedFile.learnMoreVideoIdx.eq(learnMoreVideoIdx))
                .execute();
    }
}