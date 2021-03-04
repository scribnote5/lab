package kr.ac.univ.maintenance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.maintenance.domain.MaintenanceAttachedFile;
import kr.ac.univ.maintenance.domain.QMaintenanceAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MaintenanceAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public MaintenanceAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(MaintenanceAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<MaintenanceAttachedFile> findAttachedFileByMaintenanceIdx(Long maintenanceIdx) {
        QMaintenanceAttachedFile maintenanceAttachedFile = QMaintenanceAttachedFile.maintenanceAttachedFile;

        /* SELECT *
         *   FROM AttachedFile
         *  WHERE maintenanceIdx = 'maintenanceIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(maintenanceAttachedFile)
                .where(maintenanceAttachedFile.maintenanceIdx.eq(maintenanceIdx))
                .orderBy(maintenanceAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileByMaintenanceIdx(Long maintenanceIdx) {
        QMaintenanceAttachedFile maintenanceAttachedFile = QMaintenanceAttachedFile.maintenanceAttachedFile;

        /* DELETE FROM AttachedFile
         *  WHERE maintenanceIdx = 'maintenanceIdx'
         */
        return queryFactory
                .delete(maintenanceAttachedFile)
                .where(maintenanceAttachedFile.maintenanceIdx.eq(maintenanceIdx))
                .execute();
    }
}