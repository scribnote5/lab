package kr.ac.univ.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.project.domain.QProjectAttachedFile;
import kr.ac.univ.project.domain.ProjectAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProjectAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public ProjectAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ProjectAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<ProjectAttachedFile> findAttachedFileByProjectIdx(Long projectIdx) {
        QProjectAttachedFile projectAttachedFile = QProjectAttachedFile.projectAttachedFile;

        /* SELECT *
         *   FROM AttachedFile
         *  WHERE projectIdx = 'projectIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(projectAttachedFile)
                .where(projectAttachedFile.projectIdx.eq(projectIdx))
                .orderBy(projectAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileByProjectIdx(Long projectIdx) {
        QProjectAttachedFile projectAttachedFile = QProjectAttachedFile.projectAttachedFile;

        /* DELETE FROM AttachedFile
         *  WHERE projectIdx = 'projectIdx'
         */
        return queryFactory
                .delete(projectAttachedFile)
                .where(projectAttachedFile.projectIdx.eq(projectIdx))
                .execute();
    }
}