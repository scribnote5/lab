package kr.ac.univ.guestBook.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.guestBook.listener.GuestBookListener;
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
@EntityListeners(GuestBookListener.class)
public class GuestBook extends CommonAudit {
    private String title;

    private Long mainPagePriority;

    private String content;

    @Builder
    public GuestBook(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, Long mainPagePriority, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.mainPagePriority = mainPagePriority;
        this.content = content;
    }

    public void update(GuestBook guestBook) {
        setActiveStatus(guestBook.getActiveStatus());
        this.title = guestBook.getTitle();
        this.mainPagePriority = guestBook.getMainPagePriority();
        this.content = guestBook.getContent();
    }
}