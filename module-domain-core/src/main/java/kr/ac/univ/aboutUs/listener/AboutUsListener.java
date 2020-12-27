package kr.ac.univ.aboutUs.listener;

import kr.ac.univ.aboutUs.domain.AboutUs;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class AboutUsListener {
    private final DataHistoryRepository dataHistoryRepository;

    public AboutUsListener() {
        dataHistoryRepository = null;
    }

    public AboutUsListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(AboutUs aboutUs) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(aboutUs.getCreatedDate())
                .lastModifiedDate(aboutUs.getLastModifiedDate())
                .createdBy(aboutUs.getCreatedBy())
                .lastModifiedBy(aboutUs.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(aboutUs.getIdx())
                .audClass("AboutUs")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(aboutUs.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(AboutUs aboutUs) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(aboutUs.getCreatedDate())
                .lastModifiedDate(aboutUs.getLastModifiedDate())
                .createdBy(aboutUs.getCreatedBy())
                .lastModifiedBy(aboutUs.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(aboutUs.getIdx())
                .audClass("AboutUs")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(aboutUs.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(AboutUs aboutUs) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(aboutUs.getCreatedDate())
                .lastModifiedDate(aboutUs.getLastModifiedDate())
                .createdBy(aboutUs.getCreatedBy())
                .lastModifiedBy(aboutUs.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(aboutUs.getIdx())
                .audClass("AboutUs")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(aboutUs.getTitle()))
                .build());
    }
}