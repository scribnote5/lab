package kr.ac.univ.maintenance.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.maintenance.domain.enums.MaintenanceStatus;
import kr.ac.univ.maintenance.listener.MaintenanceListener;
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
@EntityListeners(MaintenanceListener.class)
public class Maintenance extends CommonAudit {
    private String title;

    private MaintenanceStatus maintenanceStatus;

    private String content;

    @Builder
    public Maintenance(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, MaintenanceStatus maintenanceStatus, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.maintenanceStatus = maintenanceStatus;
        this.content = content;
    }

    public void update(Maintenance maintenance) {
        setActiveStatus(maintenance.getActiveStatus());
        this.title = maintenance.getTitle();
        this.maintenanceStatus = maintenance.getMaintenanceStatus();
        this.content = maintenance.getContent();
    }
}