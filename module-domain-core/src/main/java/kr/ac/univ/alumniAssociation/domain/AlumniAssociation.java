package kr.ac.univ.alumniAssociation.domain;

import kr.ac.univ.alumniAssociation.domain.enums.TitleType;
import kr.ac.univ.alumniAssociation.listener.AlumniAssociationListener;
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
@EntityListeners(AlumniAssociationListener.class)
public class AlumniAssociation extends CommonAudit {
    @Column
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private TitleType titleType;

    @Column
    private Long mainPagePriority;

    @Column
    private String content;

    @Builder
    public AlumniAssociation(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, TitleType titleType, String content, Long mainPagePriority) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.titleType = titleType;
        this.content = content;
        this.mainPagePriority = mainPagePriority;
    }

    public void update(AlumniAssociation alumniAssociation) {
        setActiveStatus(alumniAssociation.getActiveStatus());
        this.title = alumniAssociation.getTitle();
        this.titleType = alumniAssociation.getTitleType();
        this.content = alumniAssociation.getContent();
        this.mainPagePriority = alumniAssociation.getMainPagePriority();
    }
}