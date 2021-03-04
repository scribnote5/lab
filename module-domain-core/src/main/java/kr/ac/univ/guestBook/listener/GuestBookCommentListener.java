package kr.ac.univ.guestBook.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.guestBook.domain.GuestBookComment;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class GuestBookCommentListener {
    private final DataHistoryRepository dataHistoryRepository;

    public GuestBookCommentListener() {
        dataHistoryRepository = null;
    }

    public GuestBookCommentListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(GuestBookComment guestBookComment) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(guestBookComment.getCreatedDate())
                .lastModifiedDate(guestBookComment.getLastModifiedDate())
                .createdBy(guestBookComment.getCreatedBy())
                .lastModifiedBy(guestBookComment.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(guestBookComment.getIdx())
                .audClass("GuestBookComment")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(guestBookComment.getContent()))
                .build());
    }

    @PostUpdate
    public void postUpdate(GuestBookComment guestBookComment) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(guestBookComment.getCreatedDate())
                .lastModifiedDate(guestBookComment.getLastModifiedDate())
                .createdBy(guestBookComment.getCreatedBy())
                .lastModifiedBy(guestBookComment.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(guestBookComment.getIdx())
                .audClass("GuestBookComment")
                .audMessage(AudMessageUtil.getUpdateAudMessage(guestBookComment.getContent()))
                .build());
    }

    @PostRemove
    public void postRemove(GuestBookComment guestBookComment) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(guestBookComment.getCreatedDate())
                .lastModifiedDate(guestBookComment.getLastModifiedDate())
                .createdBy(guestBookComment.getCreatedBy())
                .lastModifiedBy(guestBookComment.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(guestBookComment.getIdx())
                .audClass("GuestBookComment")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(guestBookComment.getContent()))
                .build());
    }
}