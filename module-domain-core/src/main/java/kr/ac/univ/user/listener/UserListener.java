package kr.ac.univ.user.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class UserListener {
    private final DataHistoryRepository dataHistoryRepository;

    public UserListener() {
        dataHistoryRepository = null;
    }

    public UserListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(User user) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(user.getCreatedDate())
                .lastModifiedDate(user.getLastModifiedDate())
                .createdBy(user.getCreatedBy())
                .lastModifiedBy(user.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(user.getIdx())
                .audClass("User")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(user.getUsername()))
                .build());
    }

    @PostUpdate
    public void postUpdate(User user) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(user.getCreatedDate())
                .lastModifiedDate(user.getLastModifiedDate())
                .createdBy(user.getCreatedBy())
                .lastModifiedBy(user.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(user.getIdx())
                .audClass("User")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(user.getUsername()))
                .build());
    }

    @PostRemove
    public void postRemove(User user) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(user.getCreatedDate())
                .lastModifiedDate(user.getLastModifiedDate())
                .createdBy(user.getCreatedBy())
                .lastModifiedBy(user.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(user.getIdx())
                .audClass("User")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(user.getUsername()))
                .build());
    }
}