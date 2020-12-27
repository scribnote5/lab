package kr.ac.univ.learnMoreVideo.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.learnMoreVideo.listener.LearnMoreVideoListener;
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
@EntityListeners(LearnMoreVideoListener.class)
public class LearnMoreVideo extends CommonAudit {
    @Column
    private String title;

    @Builder
    public LearnMoreVideo(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
    }

    public void update(LearnMoreVideo learnMore) {
        setActiveStatus(learnMore.getActiveStatus());
        this.title = learnMore.getTitle();
    }
}
