package kr.ac.univ.maintenance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.maintenance.domain.Maintenance;
import kr.ac.univ.maintenance.domain.QMaintenance;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MaintenanceRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public MaintenanceRepositoryImpl(JPAQueryFactory queryFactory ) {
        super(Maintenance.class);
        this.queryFactory = queryFactory;
    }

    public List<Maintenance> findByTitle(String title) {
        QMaintenance maintenance = QMaintenance.maintenance;
        /*
         * SELECT *
         *   FROM notice_board
         *  WHERE title = 'title'
         */
        return queryFactory
                .selectFrom(maintenance)
                .where(maintenance.title.eq(title))
                .fetch();
    }

    public long updateViewsByIdx(Long idx) {
        QMaintenance maintenance = QMaintenance.maintenance;
        /*
         * UPDATE notice_board
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(maintenance)
                .set(maintenance.views, maintenance.views.add(1))
                .where(maintenance.idx.eq(idx))
                .execute();
    }

}