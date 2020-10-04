package kr.ac.univ.learnMore.domain;

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
public class LearnMoreAttachedFile extends AttachedFileAudit {
    @Column
    private Long learnMoreIdx;

    @Builder
    public LearnMoreAttachedFile(String createdBy, Long learnMoreIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.learnMoreIdx = learnMoreIdx;
    }
}