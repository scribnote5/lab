package kr.ac.univ.researchField.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.researchField.domain.ResearchField;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class ResearchFieldListener {
    private final DataHistoryRepository dataHistoryRepository;

    public ResearchFieldListener() {
        dataHistoryRepository = null;
    }

    public ResearchFieldListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(ResearchField researchField) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(researchField.getCreatedDate())
                .lastModifiedDate(researchField.getLastModifiedDate())
                .createdBy(researchField.getCreatedBy())
                .lastModifiedBy(researchField.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(researchField.getIdx())
                .audClass("ResearchField")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(researchField.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(ResearchField researchField) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(researchField.getCreatedDate())
                .lastModifiedDate(researchField.getLastModifiedDate())
                .createdBy(researchField.getCreatedBy())
                .lastModifiedBy(researchField.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(researchField.getIdx())
                .audClass("ResearchField")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(researchField.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(ResearchField researchField) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(researchField.getCreatedDate())
                .lastModifiedDate(researchField.getLastModifiedDate())
                .createdBy(researchField.getCreatedBy())
                .lastModifiedBy(researchField.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(researchField.getIdx())
                .audClass("ResearchField")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(researchField.getTitle()))
                .build());
    }
}