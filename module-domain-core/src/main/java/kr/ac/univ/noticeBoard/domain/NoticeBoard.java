package kr.ac.univ.noticeBoard.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.noticeBoard.listener.NoticeBoardListener;
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
@EntityListeners(NoticeBoardListener.class)
public class NoticeBoard extends CommonAudit {
    private String title;

    private String content;

    private Long views = 0L;

    @Builder
    public NoticeBoard(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.content = content;
    }

    public void update(NoticeBoard noticeBoard) {
        setActiveStatus(noticeBoard.getActiveStatus());
        this.title = noticeBoard.getTitle();
        this.content = noticeBoard.getContent();
    }
}