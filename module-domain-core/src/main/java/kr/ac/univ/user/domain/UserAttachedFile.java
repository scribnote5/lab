package kr.ac.univ.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import kr.ac.univ.common.domain.AttachedFileAudit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
public class UserAttachedFile extends AttachedFileAudit {
    private Long userIdx;

    @Builder
    public UserAttachedFile(String createdBy, Long userIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.userIdx = userIdx;
    }
}