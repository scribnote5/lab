package kr.ac.univ.maintenance.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.maintenance.domain.Maintenance;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class MaintenanceListener {
    private final DataHistoryRepository dataHistoryRepository;

    public MaintenanceListener() {
        dataHistoryRepository = null;
    }

    public MaintenanceListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(Maintenance maintenance) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(maintenance.getCreatedDate())
                .lastModifiedDate(maintenance.getLastModifiedDate())
                .createdBy(maintenance.getCreatedBy())
                .lastModifiedBy(maintenance.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(maintenance.getIdx())
                .audClass("Maintenance")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(maintenance.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(Maintenance maintenance) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(maintenance.getCreatedDate())
                .lastModifiedDate(maintenance.getLastModifiedDate())
                .createdBy(maintenance.getCreatedBy())
                .lastModifiedBy(maintenance.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(maintenance.getIdx())
                .audClass("Maintenance")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(maintenance.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(Maintenance maintenance) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(maintenance.getCreatedDate())
                .lastModifiedDate(maintenance.getLastModifiedDate())
                .createdBy(maintenance.getCreatedBy())
                .lastModifiedBy(maintenance.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(maintenance.getIdx())
                .audClass("Maintenance")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(maintenance.getTitle()))
                .build());
    }
}