package kr.ac.univ.guestBook.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.guestBook.domain.GuestBook;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class GuestBookListener {
    private final DataHistoryRepository dataHistoryRepository;

    public GuestBookListener() {
        dataHistoryRepository = null;
    }

    public GuestBookListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(GuestBook guestBook) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(guestBook.getCreatedDate())
                .lastModifiedDate(guestBook.getLastModifiedDate())
                .createdBy(guestBook.getCreatedBy())
                .lastModifiedBy(guestBook.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(guestBook.getIdx())
                .audClass("GuestBook")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(guestBook.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(GuestBook guestBook) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(guestBook.getCreatedDate())
                .lastModifiedDate(guestBook.getLastModifiedDate())
                .createdBy(guestBook.getCreatedBy())
                .lastModifiedBy(guestBook.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(guestBook.getIdx())
                .audClass("GuestBook")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(guestBook.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(GuestBook guestBook) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(guestBook.getCreatedDate())
                .lastModifiedDate(guestBook.getLastModifiedDate())
                .createdBy(guestBook.getCreatedBy())
                .lastModifiedBy(guestBook.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(guestBook.getIdx())
                .audClass("GuestBook")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(guestBook.getTitle()))
                .build());
    }
}