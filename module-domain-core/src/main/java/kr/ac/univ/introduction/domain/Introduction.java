package kr.ac.univ.introduction.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
public class Introduction extends CommonAudit {
    @Column
    private String title;

    @Column
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