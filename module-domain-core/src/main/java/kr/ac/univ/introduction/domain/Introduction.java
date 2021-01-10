package kr.ac.univ.introduction.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.introduction.listener.IntroductionListener;
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
@EntityListeners(IntroductionListener.class)
public class Introduction extends CommonAudit {
    private String title;

    private String content;

    @Builder
    public Introduction(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.content = content;
    }

    public void update(Introduction introduction) {
        setActiveStatus(introduction.getActiveStatus());
        this.title = introduction.getTitle();
        this.content = introduction.getContent();
    }
}