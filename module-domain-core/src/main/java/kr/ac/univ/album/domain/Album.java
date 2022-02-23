package kr.ac.univ.album.domain;

import kr.ac.univ.album.listener.AlbumListener;
import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String title;

    private LocalDate photoTakenDate;

    private Long mainPagePriority;

    private String hashTags;

    private String mainHashTag;

    @Builder
    public Album(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, String hashTags, String mainHashTag, LocalDate photoTakenDate, Long mainPagePriority) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.photoTakenDate = photoTakenDate;
        this.mainPagePriority = mainPagePriority;
        this.hashTags = hashTags;
        this.mainHashTag = mainHashTag;
    }

    public void update(Album album) {
        setActiveStatus(album.getActiveStatus());
        this.title = album.getTitle();
        this.photoTakenDate = album.getPhotoTakenDate();
        this.mainPagePriority = album.getMainPagePriority();
        this.hashTags = album.getHashTags();
        this.mainHashTag = album.getMainHashTag();
    }
}