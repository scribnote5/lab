package kr.ac.univ.dataHistory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.dataHistory.domain.QDataHistory;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static kr.ac.univ.dataHistory.domain.QDataHistory.dataHistory;

@Repository
@Transactional
public class DataHistoryRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public DataHistoryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(DataHistory.class);
        this.queryFactory = queryFactory;
    }

    public long updateActiveStatusByIdx(Long idx, ActiveStatus activeStatus) {
        /*
         * UPDATE data_history
         *    SET active_status = activeStatus
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(dataHistory)
                .set(dataHistory.activeStatus, activeStatus)
                .where(dataHistory.idx.eq(idx))
                .execute();
    }

    public long updateViewsByIdx(Long idx) {
        QDataHistory dataHistory = QDataHistory.dataHistory;
        /*
         * UPDATE dataHistory
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(dataHistory)
                .set(dataHistory.views, dataHistory.views.add(1))
                .where(dataHistory.idx.eq(idx))
                .execute();
    }
}