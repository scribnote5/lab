package kr.ac.univ.noticeBoard.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.noticeBoard.listener.NoticeBoardCommentListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(NoticeBoardCommentListener.class)
public class NoticeBoardComment extends CommonAudit {
    private Long noticeBoardIdx;

    private String content;

    @Builder
    public NoticeBoardComment(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, Long noticeBoardIdx, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.noticeBoardIdx = noticeBoardIdx;
        this.content = content;
    }

    public void update(NoticeBoardComment noticeBoard) {
        setActiveStatus(noticeBoard.getActiveStatus());
        this.content = noticeBoard.getContent();
    }
}