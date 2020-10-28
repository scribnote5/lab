package kr.ac.univ.loginHistory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.loginHistory.domain.LoginHistory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static kr.ac.univ.loginHistory.domain.QLoginHistory.loginHistory;

@Repository
@Transactional
public class LoginHistoryRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public LoginHistoryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(LoginHistory.class);
        this.queryFactory = queryFactory;
    }

    public long updateActiveStatusByIdx(Long idx, ActiveStatus activeStatus) {
        /*
         * UPDATE loginHistory
         *    SET active_status = activeStatus
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(loginHistory)
                .set(loginHistory.activeStatus, activeStatus)
                .where(loginHistory.idx.eq(idx))
                .execute();
    }

}