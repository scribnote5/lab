package kr.ac.univ.introductionImage.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.introductionImage.domain.IntroductionImage;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class IntroductionImageListener {
    private final DataHistoryRepository dataHistoryRepository;

    public IntroductionImageListener() {
        dataHistoryRepository = null;
    }

    public IntroductionImageListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(IntroductionImage IitroductionImage) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(IitroductionImage.getCreatedDate())
                .lastModifiedDate(IitroductionImage.getLastModifiedDate())
                .createdBy(IitroductionImage.getCreatedBy())
                .lastModifiedBy(IitroductionImage.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(IitroductionImage.getIdx())
                .audClass("IntroductionImage")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(IitroductionImage.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(IntroductionImage IitroductionImage) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(IitroductionImage.getCreatedDate())
                .lastModifiedDate(IitroductionImage.getLastModifiedDate())
                .createdBy(IitroductionImage.getCreatedBy())
                .lastModifiedBy(IitroductionImage.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(IitroductionImage.getIdx())
                .audClass("IntroductionImage")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(IitroductionImage.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(IntroductionImage IitroductionImage) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(IitroductionImage.getCreatedDate())
                .lastModifiedDate(IitroductionImage.getLastModifiedDate())
                .createdBy(IitroductionImage.getCreatedBy())
                .lastModifiedBy(IitroductionImage.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(IitroductionImage.getIdx())
                .audClass("IntroductionImage")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(IitroductionImage.getTitle()))
                .build());
    }
}