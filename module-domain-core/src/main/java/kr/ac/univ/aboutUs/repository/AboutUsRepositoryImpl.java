package kr.ac.univ.aboutUs.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.aboutUs.domain.AboutUs;
import kr.ac.univ.aboutUs.domain.QAboutUs;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class AboutUsRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public AboutUsRepositoryImpl(JPAQueryFactory queryFactory) {
        super(AboutUs.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QAboutUs aboutUs = QAboutUs.aboutUs;
        /*
         * UPDATE about_us
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(aboutUs)
                .set(aboutUs.views, aboutUs.views.add(1))
                .where(aboutUs.idx.eq(idx))
                .execute();
    }
}