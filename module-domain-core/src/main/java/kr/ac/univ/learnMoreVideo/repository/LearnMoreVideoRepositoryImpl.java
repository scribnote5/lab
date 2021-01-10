package kr.ac.univ.learnMoreVideo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.learnMoreVideo.domain.LearnMoreVideo;
import kr.ac.univ.learnMoreVideo.domain.QLearnMoreVideo;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class LearnMoreVideoRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public LearnMoreVideoRepositoryImpl(JPAQueryFactory queryFactory) {
        super(LearnMoreVideo.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QLearnMoreVideo learnMoreVideo = QLearnMoreVideo.learnMoreVideo;
        /*
         * UPDATE learnMoreVideo
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(learnMoreVideo)
                .set(learnMoreVideo.views, learnMoreVideo.views.add(1))
                .where(learnMoreVideo.idx.eq(idx))
                .execute();
    }

}