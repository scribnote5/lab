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
    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;

    @Column
    private Long viewCount = 0L;

    @Builder
    public NoticeBoard(Long idx, String createdBy, String lastModifiedBy, String title, String content, ActiveStatus activeStatus) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        this.title = title;
        this.content = content;
        this.activeStatus = activeStatus;
    }

    public void update(NoticeBoard noticeBoard) {
        this.title = noticeBoard.getTitle();
        this.content = noticeBoard.getContent();
        this.activeStatus = noticeBoard.getActiveStatus();
    }
}