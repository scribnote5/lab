package kr.ac.univ.learnMoreRead.domain;

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
public class LearnMoreReadAttachedFile extends AttachedFileAudit {
    private Long learnMoreReadIdx;

    @Builder
    public LearnMoreReadAttachedFile(String createdBy, Long learnMoreReadIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.learnMoreReadIdx = learnMoreReadIdx;
    }
}