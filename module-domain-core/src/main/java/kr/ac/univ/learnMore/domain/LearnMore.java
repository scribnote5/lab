package kr.ac.univ.learnMore.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.learnMore.domain.enums.DownloadFileType;
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
public class LearnMore extends CommonAudit {
    @Column
    private String title;

    @Column
    private DownloadFileType downloadFileType;

    @Builder
    public LearnMore(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, DownloadFileType downloadFileType) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.downloadFileType = downloadFileType;
    }

    public void update(LearnMore learnMore) {
        setActiveStatus(learnMore.getActiveStatus());
        this.title = learnMore.getTitle();
        this.downloadFileType = learnMore.getDownloadFileType();
    }

}
