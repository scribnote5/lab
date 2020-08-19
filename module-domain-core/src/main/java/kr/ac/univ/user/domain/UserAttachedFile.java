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
    @Column
    private Long userIdx;

    @Column
    private String fileName;

    @Column
    private String savedFileName;

    @Column
    private String fileSize;

    @Builder
    public UserAttachedFile(Long userIdx, String fileName, String savedFileName, String fileSize) {
        this.userIdx = userIdx;
        this.fileName = fileName;
        this.savedFileName = savedFileName;
        this.fileSize = fileSize;
    }
}