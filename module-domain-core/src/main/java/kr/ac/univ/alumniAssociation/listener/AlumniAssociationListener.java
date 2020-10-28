package kr.ac.univ.alumniAssociation.listener;

import kr.ac.univ.alumniAssociation.domain.AlumniAssociation;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class AlumniAssociationListener {
    private final DataHistoryRepository dataHistoryRepository;

    public AlumniAssociationListener() {
        dataHistoryRepository = null;
    }

    public AlumniAssociationListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(AlumniAssociation alumniAssociation) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(alumniAssociation.getCreatedDate())
                .lastModifiedDate(alumniAssociation.getLastModifiedDate())
                .createdBy(alumniAssociation.getCreatedBy())
                .lastModifiedBy(alumniAssociation.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(alumniAssociation.getIdx())
                .audClass("AlumniAssociation")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(alumniAssociation.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(AlumniAssociation alumniAssociation) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(alumniAssociation.getCreatedDate())
                .lastModifiedDate(alumniAssociation.getLastModifiedDate())
                .createdBy(alumniAssociation.getCreatedBy())
                .lastModifiedBy(alumniAssociation.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(alumniAssociation.getIdx())
                .audClass("AlumniAssociation")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(alumniAssociation.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(AlumniAssociation alumniAssociation) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(alumniAssociation.getCreatedDate())
                .lastModifiedDate(alumniAssociation.getLastModifiedDate())
                .createdBy(alumniAssociation.getCreatedBy())
                .lastModifiedBy(alumniAssociation.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(alumniAssociation.getIdx())
                .audClass("AlumniAssociation")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(alumniAssociation.getTitle()))
                .build());
    }
}