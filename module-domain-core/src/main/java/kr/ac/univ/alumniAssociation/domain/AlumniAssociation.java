package kr.ac.univ.alumniAssociation.domain;

import kr.ac.univ.alumniAssociation.listener.AlumniAssociationListener;
import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
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
@EntityListeners(AlumniAssociationListener.class)
public class AlumniAssociation extends CommonAudit {
    @Column
    private String title;

    @Column
    private String content;

    @Builder
    public AlumniAssociation(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.content = content;
    }

    public void update(AlumniAssociation alumniAssociation) {
        setActiveStatus(alumniAssociation.getActiveStatus());
        this.title = alumniAssociation.getTitle();
        this.content = alumniAssociation.getContent();
    }
}