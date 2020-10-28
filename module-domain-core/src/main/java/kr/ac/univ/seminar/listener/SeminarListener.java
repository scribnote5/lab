package kr.ac.univ.seminar.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.seminar.domain.Seminar;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class SeminarListener {
    private final DataHistoryRepository dataHistoryRepository;

    public SeminarListener() {
        dataHistoryRepository = null;
    }

    public SeminarListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(Seminar seminar) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(seminar.getCreatedDate())
                .lastModifiedDate(seminar.getLastModifiedDate())
                .createdBy(seminar.getCreatedBy())
                .lastModifiedBy(seminar.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(seminar.getIdx())
                .audClass("Seminar")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(seminar.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(Seminar seminar) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(seminar.getCreatedDate())
                .lastModifiedDate(seminar.getLastModifiedDate())
                .createdBy(seminar.getCreatedBy())
                .lastModifiedBy(seminar.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(seminar.getIdx())
                .audClass("Seminar")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(seminar.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(Seminar seminar) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(seminar.getCreatedDate())
                .lastModifiedDate(seminar.getLastModifiedDate())
                .createdBy(seminar.getCreatedBy())
                .lastModifiedBy(seminar.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(seminar.getIdx())
                .audClass("Seminar")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(seminar.getTitle()))
                .build());
    }
}