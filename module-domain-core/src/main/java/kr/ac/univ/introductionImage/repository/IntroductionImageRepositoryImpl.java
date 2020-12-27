package kr.ac.univ.introductionImage.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.introductionImage.domain.IntroductionImageAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static kr.ac.univ.introductionImage.domain.QIntroductionImageAttachedFile.introductionImageAttachedFile;

@Repository
@Transactional
public class IntroductionImageRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public IntroductionImageRepositoryImpl(JPAQueryFactory queryFactory) {
        super(IntroductionImageAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<IntroductionImageAttachedFile> findAttachedFileByIntroductionImageIdx(Long introductionImageIdx) {
         /* SELECT *
         *   FROM AttachedFile
         *  WHERE introductionImageIdx = 'introductionImageIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(introductionImageAttachedFile)
                .where(introductionImageAttachedFile.introductionImageIdx.eq(introductionImageIdx))
                .orderBy(introductionImageAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileByIntroductionImageIdx(Long introductionImageIdx) {
        /* DELETE FROM AttachedFile
         *  WHERE introductionImageIdx = 'introductionImageIdx'
         */
        return queryFactory
                .delete(introductionImageAttachedFile)
                .where(introductionImageAttachedFile.introductionImageIdx.eq(introductionImageIdx))
                .execute();
    }
}