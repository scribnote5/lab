package kr.ac.univ.seminar.domain;

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
public class SeminarAttachedFile extends AttachedFileAudit {
    private Long seminarIdx;

    @Builder
    public SeminarAttachedFile(String createdBy, Long seminarIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.seminarIdx = seminarIdx;
    }
}
