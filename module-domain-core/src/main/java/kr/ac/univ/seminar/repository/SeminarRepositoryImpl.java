package kr.ac.univ.seminar.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.publication.dto.PublicationSearchDto;
import kr.ac.univ.seminar.domain.Seminar;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import java.util.List;

import static kr.ac.univ.publication.domain.QPublication.publication;
import static kr.ac.univ.seminar.domain.QSeminar.seminar;
import static kr.ac.univ.user.domain.QUser.user;

@Repository
@Transactional
public class SeminarRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public SeminarRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Seminar.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {

        /*
         * UPDATE seminar
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(seminar)
                .set(seminar.views, seminar.views.add(1))
                .where(seminar.idx.eq(idx))
                .execute();
    }

    public PageImpl<Seminar> findSeminarByUserEnglishName(Pageable pageable, String englishName, String moduleName) {
        /*
         * SELECT *
         * FROM seminar
         * LEFT JOIN user on seminar.created_by = user.username
         * WHERE user.username CONCAT('%', englishName ,'%');
         */
        JPQLQuery<Seminar> query =
                queryFactory
                        .select(seminar)
                        .from(seminar)
                        .leftJoin(user).on(seminar.createdBy.eq(user.username))
                        .where(user.englishName.contains(englishName),
                                eqModuleName(moduleName))
                        .orderBy(seminar.presentationDate.desc());

        Long totalCount = query.fetchCount();
        List<Seminar> results = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(results, pageable, totalCount);
    }

    private BooleanExpression eqModuleName(String moduleName) {
        BooleanExpression result = null;

        // module-app-admin은 active status와 관계 없이 모든 seminar를 조회

        if ("module-app-web".equals(moduleName)) {
            result = seminar.activeStatus.eq(ActiveStatus.ACTIVE);
        }

        return result;
    }
}