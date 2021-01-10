package kr.ac.univ.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.category.domain.Category;
import kr.ac.univ.category.domain.QCategory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class CategoryRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(JPAQueryFactory queryFactory ) {
        super(Category.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QCategory category = QCategory.category;
        /*
         * UPDATE category
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(category)
                .set(category.views, category.views.add(1))
                .where(category.idx.eq(idx))
                .execute();
    }

}