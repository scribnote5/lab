package kr.ac.univ.aboutUs.domain;

import kr.ac.univ.aboutUs.listener.AboutUsListener;
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
@EntityListeners(AboutUsListener.class)
public class AboutUs extends CommonAudit {
    @Column
    private String title;

    @Column
    private String content;

    @Builder
    public AboutUs(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.content = content;
    }

    public void update(AboutUs aboutUs) {
        setActiveStatus(aboutUs.getActiveStatus());
        this.title = aboutUs.getTitle();
        this.content = aboutUs.getContent();
    }
}