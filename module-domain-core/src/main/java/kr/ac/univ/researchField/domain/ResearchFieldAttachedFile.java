package kr.ac.univ.researchField.domain;

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
public class ResearchFieldAttachedFile extends AttachedFileAudit {
    private Long researchFieldIdx;

    @Builder
    public ResearchFieldAttachedFile(String createdBy, Long researchFieldIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.researchFieldIdx = researchFieldIdx;
    }
}
