package kr.ac.univ.seminar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.seminar.domain.SeminarAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static kr.ac.univ.seminar.domain.QSeminarAttachedFile.seminarAttachedFile;

@Repository
@Transactional
public class SeminarAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public SeminarAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(SeminarAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<SeminarAttachedFile> findAttachedFileBySeminarIdx(Long seminarIdx) {

        /* SELECT *
         *   FROM AttachedFile
         *  WHERE seminarIdx = 'seminarIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(seminarAttachedFile)
                .where(seminarAttachedFile.seminarIdx.eq(seminarIdx))
                .orderBy(seminarAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileBySeminarIdx(Long seminarIdx) {

        /* DELETE FROM AttachedFile
         *  WHERE seminarIdx = 'seminarIdx'
         */
        return queryFactory
                .delete(seminarAttachedFile)
                .where(seminarAttachedFile.seminarIdx.eq(seminarIdx))
                .execute();
    }
}