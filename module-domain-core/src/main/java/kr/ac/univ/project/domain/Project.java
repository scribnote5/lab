package kr.ac.univ.project.domain;

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
public class Project extends CommonAudit {
    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Long views = 0L;

    @Column
    private Long researchFieldIdx = 0L;

    @Builder
    public Project(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, String content, Long researchFieldIdx) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.content = content;
        this.researchFieldIdx = researchFieldIdx;
    }

    public void update(Project project) {
        setActiveStatus(project.getActiveStatus());
        this.title = project.getTitle();
        this.content = project.getContent();
        this.researchFieldIdx = project.getResearchFieldIdx();
    }
}