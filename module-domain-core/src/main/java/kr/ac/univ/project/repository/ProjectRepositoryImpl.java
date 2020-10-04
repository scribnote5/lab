package kr.ac.univ.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.project.domain.Project;
import kr.ac.univ.project.domain.QProject;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static kr.ac.univ.project.domain.QProject.project;

@Repository
@Transactional
public class ProjectRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public ProjectRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Project.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        /*
         * UPDATE notice_board
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(project)
                .set(project.views, project.views.add(1))
                .where(project.idx.eq(idx))
                .execute();
    }

}