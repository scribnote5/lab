package kr.ac.univ.researchField.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.researchField.listener.ResearchFieldListener;
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
@EntityListeners(ResearchFieldListener.class)
public class ResearchField extends CommonAudit {
    private String title;

    private Long categoryIdx = 0L;

    private String content;

    @Builder
    public ResearchField(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, Long categoryIdx, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.categoryIdx = categoryIdx;
        this.content = content;
    }

    public void update(ResearchField researchField) {
        setActiveStatus(researchField.getActiveStatus());
        this.title = researchField.getTitle();
        this.categoryIdx = researchField.getCategoryIdx();
        this.content = researchField.getContent();
    }
}