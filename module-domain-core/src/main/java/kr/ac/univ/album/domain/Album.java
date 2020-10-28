package kr.ac.univ.album.domain;

import kr.ac.univ.album.listener.AlbumListener;
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
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(AlbumListener.class)
public class Album extends CommonAudit {
    @Column
    private String title;

    @Column
    private LocalDate photoTakenDate;

    @Column
    private Long mainPagePriority;

    @Column
    private String hashTags;

    @Builder
    public Album(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, String hashTags, LocalDate photoTakenDate, Long mainPagePriority) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.photoTakenDate = photoTakenDate;
        this.mainPagePriority = mainPagePriority;
        this.hashTags = hashTags;
    }

    public void update(Album project) {
        setActiveStatus(project.getActiveStatus());
        this.title = project.getTitle();
        this.photoTakenDate = project.getPhotoTakenDate();
        this.mainPagePriority = project.getMainPagePriority();
        this.hashTags = project.getHashTags();
    }
}