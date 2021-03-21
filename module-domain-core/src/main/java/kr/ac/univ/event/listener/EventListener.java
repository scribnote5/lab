package kr.ac.univ.event.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.event.domain.Event;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class EventListener {
    private final DataHistoryRepository dataHistoryRepository;

    public EventListener() {
        dataHistoryRepository = null;
    }

    public EventListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }
    
    @PostPersist
    public void postPersist(Event event) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(event.getCreatedDate())
                .lastModifiedDate(event.getLastModifiedDate())
                .createdBy(event.getCreatedBy())
                .lastModifiedBy(event.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(event.getIdx())
                .audClass("Event")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(event.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(Event event) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(event.getCreatedDate())
                .lastModifiedDate(event.getLastModifiedDate())
                .createdBy(event.getCreatedBy())
                .lastModifiedBy(event.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(event.getIdx())
                .audClass("Event")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(event.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(Event event) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(event.getCreatedDate())
                .lastModifiedDate(event.getLastModifiedDate())
                .createdBy(event.getCreatedBy())
                .lastModifiedBy(event.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(event.getIdx())
                .audClass("Event")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(event.getTitle()))
                .build());
    }
}