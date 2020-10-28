package kr.ac.univ.noticeBoard.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class NoticeBoardListener {
    private final DataHistoryRepository dataHistoryRepository;

    public NoticeBoardListener() {
        dataHistoryRepository = null;
    }

    public NoticeBoardListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(NoticeBoard noticeBoard) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(noticeBoard.getCreatedDate())
                .lastModifiedDate(noticeBoard.getLastModifiedDate())
                .createdBy(noticeBoard.getCreatedBy())
                .lastModifiedBy(noticeBoard.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(noticeBoard.getIdx())
                .audClass("NoticeBoard")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(noticeBoard.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(NoticeBoard noticeBoard) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(noticeBoard.getCreatedDate())
                .lastModifiedDate(noticeBoard.getLastModifiedDate())
                .createdBy(noticeBoard.getCreatedBy())
                .lastModifiedBy(noticeBoard.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(noticeBoard.getIdx())
                .audClass("NoticeBoard")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(noticeBoard.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(NoticeBoard noticeBoard) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(noticeBoard.getCreatedDate())
                .lastModifiedDate(noticeBoard.getLastModifiedDate())
                .createdBy(noticeBoard.getCreatedBy())
                .lastModifiedBy(noticeBoard.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(noticeBoard.getIdx())
                .audClass("NoticeBoard")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(noticeBoard.getTitle()))
                .build());
    }
}