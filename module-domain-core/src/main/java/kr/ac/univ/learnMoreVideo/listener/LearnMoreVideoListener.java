package kr.ac.univ.learnMoreVideo.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.learnMoreVideo.domain.LearnMoreVideo;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class LearnMoreVideoListener {
    private final DataHistoryRepository dataHistoryRepository;

    public LearnMoreVideoListener() {
        dataHistoryRepository = null;
    }

    public LearnMoreVideoListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(LearnMoreVideo learnMore) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(learnMore.getCreatedDate())
                .lastModifiedDate(learnMore.getLastModifiedDate())
                .createdBy(learnMore.getCreatedBy())
                .lastModifiedBy(learnMore.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(learnMore.getIdx())
                .audClass("LearnMoreVideo")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(learnMore.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(LearnMoreVideo learnMore) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(learnMore.getCreatedDate())
                .lastModifiedDate(learnMore.getLastModifiedDate())
                .createdBy(learnMore.getCreatedBy())
                .lastModifiedBy(learnMore.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(learnMore.getIdx())
                .audClass("LearnMoreVideo")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(learnMore.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(LearnMoreVideo learnMore) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(learnMore.getCreatedDate())
                .lastModifiedDate(learnMore.getLastModifiedDate())
                .createdBy(learnMore.getCreatedBy())
                .lastModifiedBy(learnMore.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(learnMore.getIdx())
                .audClass("LearnMoreVideo")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(learnMore.getTitle()))
                .build());
    }
}