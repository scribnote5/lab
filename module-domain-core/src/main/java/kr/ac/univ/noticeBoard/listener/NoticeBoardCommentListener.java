package kr.ac.univ.noticeBoard.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.noticeBoard.domain.NoticeBoardComment;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class NoticeBoardCommentListener {
    private final DataHistoryRepository dataHistoryRepository;

    public NoticeBoardCommentListener() {
        dataHistoryRepository = null;
    }

    public NoticeBoardCommentListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(NoticeBoardComment noticeBoardComment) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(noticeBoardComment.getCreatedDate())
                .lastModifiedDate(noticeBoardComment.getLastModifiedDate())
                .createdBy(noticeBoardComment.getCreatedBy())
                .lastModifiedBy(noticeBoardComment.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(noticeBoardComment.getIdx())
                .audClass("NoticeBoardComment")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(noticeBoardComment.getContent()))
                .build());
    }

    @PostUpdate
    public void postUpdate(NoticeBoardComment noticeBoardComment) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(noticeBoardComment.getCreatedDate())
                .lastModifiedDate(noticeBoardComment.getLastModifiedDate())
                .createdBy(noticeBoardComment.getCreatedBy())
                .lastModifiedBy(noticeBoardComment.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(noticeBoardComment.getIdx())
                .audClass("NoticeBoardComment")
                .audMessage(AudMessageUtil.getUpdateAudMessage(noticeBoardComment.getContent()))
                .build());
    }

    @PostRemove
    public void postRemove(NoticeBoardComment noticeBoardComment) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(noticeBoardComment.getCreatedDate())
                .lastModifiedDate(noticeBoardComment.getLastModifiedDate())
                .createdBy(noticeBoardComment.getCreatedBy())
                .lastModifiedBy(noticeBoardComment.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(noticeBoardComment.getIdx())
                .audClass("NoticeBoardComment")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(noticeBoardComment.getContent()))
                .build());
    }
}