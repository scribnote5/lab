package kr.ac.univ.project.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.project.domain.enums.ProjectStatus;
import kr.ac.univ.project.listener.ProjectListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(ProjectListener.class)
public class Project extends CommonAudit {
    private String title;

    private String content;

    private Long researchFieldIdx = 0L;

    private String researchEstablishment;

    private ProjectStatus projectStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    @Builder
    public Project(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, String content, Long researchFieldIdx, String researchEstablishment, ProjectStatus projectStatus, LocalDate startDate,
                   LocalDate endDate) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.content = content;
        this.researchFieldIdx = researchFieldIdx;
        this.researchEstablishment = researchEstablishment;
        this.projectStatus = projectStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(Project project) {
        setActiveStatus(project.getActiveStatus());
        this.title = project.getTitle();
        this.content = project.getContent();
        this.researchFieldIdx = project.getResearchFieldIdx();
        this.researchEstablishment = project.getResearchEstablishment();
        this.projectStatus = project.getProjectStatus();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
    }
}