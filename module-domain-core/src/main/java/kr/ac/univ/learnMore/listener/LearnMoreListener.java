package kr.ac.univ.learnMore.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.learnMore.domain.LearnMore;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class LearnMoreListener {
    private final DataHistoryRepository dataHistoryRepository;

    public LearnMoreListener() {
        dataHistoryRepository = null;
    }

    public LearnMoreListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(LearnMore learnMore) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(learnMore.getCreatedDate())
                .lastModifiedDate(learnMore.getLastModifiedDate())
                .createdBy(learnMore.getCreatedBy())
                .lastModifiedBy(learnMore.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(learnMore.getIdx())
                .audClass("LearnMore")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(learnMore.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(LearnMore learnMore) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(learnMore.getCreatedDate())
                .lastModifiedDate(learnMore.getLastModifiedDate())
                .createdBy(learnMore.getCreatedBy())
                .lastModifiedBy(learnMore.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(learnMore.getIdx())
                .audClass("LearnMore")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(learnMore.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(LearnMore learnMore) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(learnMore.getCreatedDate())
                .lastModifiedDate(learnMore.getLastModifiedDate())
                .createdBy(learnMore.getCreatedBy())
                .lastModifiedBy(learnMore.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(learnMore.getIdx())
                .audClass("LearnMore")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(learnMore.getTitle()))
                .build());
    }
}