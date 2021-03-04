package kr.ac.univ.maintenance.domain;


import kr.ac.univ.common.domain.AttachedFileAudit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
public class MaintenanceAttachedFile extends AttachedFileAudit {
    private Long maintenanceIdx;

    @Builder
    public MaintenanceAttachedFile(String createdBy, Long maintenanceIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.maintenanceIdx = maintenanceIdx;
    }
}
