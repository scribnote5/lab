package kr.ac.univ.noticeBoard.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
public class NoticeBoard extends CommonAudit {
    @Column
    private String title;

    @Column
    private String content;


    @Column
    private Long viewCount = 0L;

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