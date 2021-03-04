package kr.ac.univ.maintenance.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.maintenance.domain.MaintenanceComment;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class MaintenanceCommentListener {
    private final DataHistoryRepository dataHistoryRepository;

    public MaintenanceCommentListener() {
        dataHistoryRepository = null;
    }

    public MaintenanceCommentListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(MaintenanceComment maintenanceComment) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(maintenanceComment.getCreatedDate())
                .lastModifiedDate(maintenanceComment.getLastModifiedDate())
                .createdBy(maintenanceComment.getCreatedBy())
                .lastModifiedBy(maintenanceComment.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(maintenanceComment.getIdx())
                .audClass("MaintenanceComment")
                .audType(AudType.INSERT)
                .audMessage("The maintenance comment is registered.")
                .build());
    }

    @PostUpdate
    public void postUpdate(MaintenanceComment maintenanceComment) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(maintenanceComment.getCreatedDate())
                .lastModifiedDate(maintenanceComment.getLastModifiedDate())
                .createdBy(maintenanceComment.getCreatedBy())
                .lastModifiedBy(maintenanceComment.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(maintenanceComment.getIdx())
                .audClass("MaintenanceComment")
                .audMessage("The maintenance comment is updated.")
                .build());
    }

    @PostRemove
    public void postRemove(MaintenanceComment maintenanceComment) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(maintenanceComment.getCreatedDate())
                .lastModifiedDate(maintenanceComment.getLastModifiedDate())
                .createdBy(maintenanceComment.getCreatedBy())
                .lastModifiedBy(maintenanceComment.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(maintenanceComment.getIdx())
                .audClass("MaintenanceComment")
                .audType(AudType.DELETE)
                .audMessage("The maintenance comment is deleted.")
                .build());
    }
}