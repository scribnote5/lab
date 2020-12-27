package kr.ac.univ.introductionImage.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.introductionImage.listener.IntroductionImageListener;
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
@EntityListeners(IntroductionImageListener.class)
public class IntroductionImage extends CommonAudit {
    @Column
    private String title;

    @Column
    private Long mainPagePriority;

    @Builder
    public IntroductionImage(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, Long mainPagePriority) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.mainPagePriority = mainPagePriority;
    }

    public void update(IntroductionImage project) {
        setActiveStatus(project.getActiveStatus());
        this.title = project.getTitle();
        this.mainPagePriority = project.getMainPagePriority();
    }
}