package kr.ac.univ.introduction.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.introduction.domain.Introduction;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class IntroductionListener {
    private final DataHistoryRepository dataHistoryRepository;

    public IntroductionListener() {
        dataHistoryRepository = null;
    }

    public IntroductionListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(Introduction introduction) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(introduction.getCreatedDate())
                .lastModifiedDate(introduction.getLastModifiedDate())
                .createdBy(introduction.getCreatedBy())
                .lastModifiedBy(introduction.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(introduction.getIdx())
                .audClass("Introduction")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(introduction.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(Introduction introduction) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(introduction.getCreatedDate())
                .lastModifiedDate(introduction.getLastModifiedDate())
                .createdBy(introduction.getCreatedBy())
                .lastModifiedBy(introduction.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(introduction.getIdx())
                .audClass("Introduction")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(introduction.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(Introduction introduction) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(introduction.getCreatedDate())
                .lastModifiedDate(introduction.getLastModifiedDate())
                .createdBy(introduction.getCreatedBy())
                .lastModifiedBy(introduction.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(introduction.getIdx())
                .audClass("Introduction")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getInsertAudMessage(introduction.getTitle()))
                .build());
    }
}