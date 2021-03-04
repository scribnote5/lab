package kr.ac.univ.guestBook.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.guestBook.listener.GuestBookCommentListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(GuestBookCommentListener.class)
public class GuestBookComment extends CommonAudit {
    private Long guestBookIdx;

    private String content;

    @Builder
    public GuestBookComment(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, Long guestBookIdx, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.guestBookIdx = guestBookIdx;
        this.content = content;
    }

    public void update(GuestBookComment guestBook) {
        setActiveStatus(guestBook.getActiveStatus());
        this.content = guestBook.getContent();
    }
}