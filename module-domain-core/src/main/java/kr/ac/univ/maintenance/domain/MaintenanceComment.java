package kr.ac.univ.maintenance.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.maintenance.listener.MaintenanceCommentListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(MaintenanceCommentListener.class)
public class MaintenanceComment extends CommonAudit {
    private Long maintenanceIdx;

    private String content;

    @Builder
    public MaintenanceComment(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, Long maintenanceIdx, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.maintenanceIdx = maintenanceIdx;
        this.content = content;
    }

    public void update(MaintenanceComment maintenance) {
        setActiveStatus(maintenance.getActiveStatus());
        this.content = maintenance.getContent();
    }
}