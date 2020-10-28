package kr.ac.univ.email.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.email.domain.Email;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class EmailListener {
    private final DataHistoryRepository dataHistoryRepository;

    public EmailListener() {
        dataHistoryRepository = null;
    }

    public EmailListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(Email email) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(email.getCreatedDate())
                .lastModifiedDate(email.getLastModifiedDate())
                .createdBy(email.getCreatedBy())
                .lastModifiedBy(email.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(email.getIdx())
                .audClass("Email")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(email.getEmailAddress()))
                .build());
    }

    @PostUpdate
    public void postUpdate(Email email) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(email.getCreatedDate())
                .lastModifiedDate(email.getLastModifiedDate())
                .createdBy(email.getCreatedBy())
                .lastModifiedBy(email.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(email.getIdx())
                .audClass("Email")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(email.getEmailAddress()))
                .build());
    }

    @PostRemove
    public void postRemove(Email email) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(email.getCreatedDate())
                .lastModifiedDate(email.getLastModifiedDate())
                .createdBy(email.getCreatedBy())
                .lastModifiedBy(email.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(email.getIdx())
                .audClass("Email")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getInsertAudMessage(email.getEmailAddress()))
                .build());
    }
}