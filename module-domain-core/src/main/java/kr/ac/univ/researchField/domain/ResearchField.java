package kr.ac.univ.researchField.domain;

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
public class ResearchField extends CommonAudit {
    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private String content;
    
    @Builder
    public ResearchField(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, String subTitle, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
    }

    public void update(ResearchField subTtitle) {
        setActiveStatus(subTtitle.getActiveStatus());
        this.title = subTtitle.getTitle();
        this.subTitle = subTtitle.getSubTitle();
        this.content = subTtitle.getContent();
    }
}