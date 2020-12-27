package kr.ac.univ.learnMoreRead.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.learnMoreRead.domain.LearnMoreRead;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class LearnMoreReadListener {
    private final DataHistoryRepository dataHistoryRepository;

    public LearnMoreReadListener() {
        dataHistoryRepository = null;
    }

    public LearnMoreReadListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(LearnMoreRead learnMore) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(learnMore.getCreatedDate())
                .lastModifiedDate(learnMore.getLastModifiedDate())
                .createdBy(learnMore.getCreatedBy())
                .lastModifiedBy(learnMore.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(learnMore.getIdx())
                .audClass("LearnMoreRead")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(learnMore.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(LearnMoreRead learnMore) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(learnMore.getCreatedDate())
                .lastModifiedDate(learnMore.getLastModifiedDate())
                .createdBy(learnMore.getCreatedBy())
                .lastModifiedBy(learnMore.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(learnMore.getIdx())
                .audClass("LearnMoreRead")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(learnMore.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(LearnMoreRead learnMore) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(learnMore.getCreatedDate())
                .lastModifiedDate(learnMore.getLastModifiedDate())
                .createdBy(learnMore.getCreatedBy())
                .lastModifiedBy(learnMore.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(learnMore.getIdx())
                .audClass("LearnMoreRead")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(learnMore.getTitle()))
                .build());
    }
}