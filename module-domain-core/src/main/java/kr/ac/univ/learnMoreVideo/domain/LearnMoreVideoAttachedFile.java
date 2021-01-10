package kr.ac.univ.learnMoreVideo.domain;

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
public class LearnMoreVideoAttachedFile extends AttachedFileAudit {
    private Long learnMoreVideoIdx;

    @Builder
    public LearnMoreVideoAttachedFile(String createdBy, Long learnMoreVideoIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.learnMoreVideoIdx = learnMoreVideoIdx;
    }
}