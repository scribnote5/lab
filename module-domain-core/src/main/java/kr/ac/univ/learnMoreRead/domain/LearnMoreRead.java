package kr.ac.univ.learnMoreRead.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.learnMoreRead.listener.LearnMoreReadListener;
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
@EntityListeners(LearnMoreReadListener.class)
public class LearnMoreRead extends CommonAudit {
    private String title;

    @Builder
    public LearnMoreRead(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
    }

    public void update(LearnMoreRead learnMore) {
        setActiveStatus(learnMore.getActiveStatus());
        this.title = learnMore.getTitle();
    }
}
