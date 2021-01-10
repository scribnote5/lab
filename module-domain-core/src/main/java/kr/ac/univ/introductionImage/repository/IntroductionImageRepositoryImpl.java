package kr.ac.univ.introductionImage.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.introductionImage.domain.IntroductionImage;
import kr.ac.univ.introductionImage.domain.QIntroductionImage;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class IntroductionImageRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public IntroductionImageRepositoryImpl(JPAQueryFactory queryFactory) {
        super(IntroductionImage.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QIntroductionImage introductionImage = QIntroductionImage.introductionImage;
        /*
         * UPDATE introductionImage
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(introductionImage)
                .set(introductionImage.views, introductionImage.views.add(1))
                .where(introductionImage.idx.eq(idx))
                .execute();
    }

}