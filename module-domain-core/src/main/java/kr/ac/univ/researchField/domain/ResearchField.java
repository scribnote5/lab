package kr.ac.univ.researchField.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.project.domain.enums.ProjectStatus;
import kr.ac.univ.researchField.domain.enums.ResearchFieldStatus;
import kr.ac.univ.researchField.listener.ResearchFieldListener;
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
@EntityListeners(ResearchFieldListener.class)
public class ResearchField extends CommonAudit {
    private String title;

    private Long categoryIdx = 0L;

    @Enumerated(EnumType.STRING)
    private ResearchFieldStatus researchFieldStatus;

    private String content;

    @Builder
    public ResearchField(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, Long categoryIdx, ResearchFieldStatus researchFieldStatus, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.categoryIdx = categoryIdx;
        this.researchFieldStatus = researchFieldStatus;
        this.content = content;
    }

    public void update(ResearchField researchField) {
        setActiveStatus(researchField.getActiveStatus());
        this.title = researchField.getTitle();
        this.categoryIdx = researchField.getCategoryIdx();
        this.researchFieldStatus = researchField.getResearchFieldStatus();
        this.content = researchField.getContent();
    }
}