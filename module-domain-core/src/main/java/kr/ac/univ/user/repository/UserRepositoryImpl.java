package kr.ac.univ.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.domain.QUser;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QUser user = QUser.user;
        /*
         * UPDATE user
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(user)
                .set(user.views, user.views.add(1))
                .where(user.idx.eq(idx))
                .execute();
    }
}