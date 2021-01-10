package kr.ac.univ.researchField.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.researchField.domain.ResearchFieldAttachedFile;
import kr.ac.univ.researchField.domain.QResearchFieldAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ResearchFieldAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public ResearchFieldAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ResearchFieldAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<ResearchFieldAttachedFile> findAttachedFileByResearchFieldIdx(Long researchFieldIdx) {
        QResearchFieldAttachedFile researchFieldAttachedFile = QResearchFieldAttachedFile.researchFieldAttachedFile;

        /* SELECT *
         *   FROM AttachedFile
         *  WHERE researchFieldIdx = 'researchFieldIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(researchFieldAttachedFile)
                .where(researchFieldAttachedFile.researchFieldIdx.eq(researchFieldIdx))
                .orderBy(researchFieldAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileByResearchFieldIdx(Long researchFieldIdx) {
        QResearchFieldAttachedFile researchFieldAttachedFile = QResearchFieldAttachedFile.researchFieldAttachedFile;

        /* DELETE FROM AttachedFile
         *  WHERE researchFieldIdx = 'researchFieldIdx'
         */
        return queryFactory
                .delete(researchFieldAttachedFile)
                .where(researchFieldAttachedFile.researchFieldIdx.eq(researchFieldIdx))
                .execute();
    }
}