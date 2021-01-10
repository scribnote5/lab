package kr.ac.univ.setting.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.setting.domain.Setting;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class SettingListener {
    private final DataHistoryRepository dataHistoryRepository;

    public SettingListener() {
        dataHistoryRepository = null;
    }

    public SettingListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(Setting introduction) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(introduction.getCreatedDate())
                .lastModifiedDate(introduction.getLastModifiedDate())
                .createdBy(introduction.getCreatedBy())
                .lastModifiedBy(introduction.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(introduction.getIdx())
                .audClass("Setting")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage("Setting is changed"))
                .build());
    }

    @PostUpdate
    public void postUpdate(Setting introduction) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(introduction.getCreatedDate())
                .lastModifiedDate(introduction.getLastModifiedDate())
                .createdBy(introduction.getCreatedBy())
                .lastModifiedBy(introduction.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(introduction.getIdx())
                .audClass("Setting")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage("Setting is changed"))
                .build());
    }

    @PostRemove
    public void postRemove(Setting introduction) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(introduction.getCreatedDate())
                .lastModifiedDate(introduction.getLastModifiedDate())
                .createdBy(introduction.getCreatedBy())
                .lastModifiedBy(introduction.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(introduction.getIdx())
                .audClass("Setting")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getInsertAudMessage("Setting is changed"))
                .build());
    }
}