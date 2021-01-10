package kr.ac.univ.alumniAssociation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.alumniAssociation.domain.AlumniAssociation;
import kr.ac.univ.alumniAssociation.domain.QAlumniAssociation;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class AlumniAssociationRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public AlumniAssociationRepositoryImpl(JPAQueryFactory queryFactory) {
        super(AlumniAssociation.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QAlumniAssociation alumniAssociation = QAlumniAssociation.alumniAssociation;
        /*
         * UPDATE notice_board
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(alumniAssociation)
                .set(alumniAssociation.views, alumniAssociation.views.add(1))
                .where(alumniAssociation.idx.eq(idx))
                .execute();
    }

}