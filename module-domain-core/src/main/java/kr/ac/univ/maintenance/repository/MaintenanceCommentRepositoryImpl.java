package kr.ac.univ.maintenance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.maintenance.domain.MaintenanceComment;
import kr.ac.univ.maintenance.domain.QMaintenanceComment;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class MaintenanceCommentRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public MaintenanceCommentRepositoryImpl(JPAQueryFactory queryFactory) {
        super(MaintenanceComment.class);
        this.queryFactory = queryFactory;
    }

    public Long deleteAllByMaintenanceIdx(Long maintenanceIdx) {
        QMaintenanceComment maintenanceComment = QMaintenanceComment.maintenanceComment;

        /*
         * DELETE FROM AttachedFile
         * WHERE maintenanceIdx = 'maintenanceIdx'
         */
        return queryFactory
                .delete(maintenanceComment)
                .where(maintenanceComment.maintenanceIdx.eq(maintenanceIdx))
                .execute();
    }
}