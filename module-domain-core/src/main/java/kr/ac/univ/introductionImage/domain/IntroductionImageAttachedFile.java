package kr.ac.univ.introductionImage.domain;


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
public class IntroductionImageAttachedFile extends AttachedFileAudit {
    @Column
    private Long introductionImageIdx;

    @Builder
    public IntroductionImageAttachedFile(String createdBy, Long introductionImageIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.introductionImageIdx = introductionImageIdx;
    }
}
