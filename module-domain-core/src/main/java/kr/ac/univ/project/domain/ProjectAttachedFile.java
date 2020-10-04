package kr.ac.univ.project.domain;


import kr.ac.univ.common.domain.AttachedFileAudit;
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
public class ProjectAttachedFile extends AttachedFileAudit {
    @Column
    private Long projectIdx;

    @Builder
    public ProjectAttachedFile(String createdBy, Long projectIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.projectIdx = projectIdx;
    }
}
