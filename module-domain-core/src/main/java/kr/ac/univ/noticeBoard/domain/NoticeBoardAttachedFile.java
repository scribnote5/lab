package kr.ac.univ.noticeBoard.domain;


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
public class NoticeBoardAttachedFile extends AttachedFileAudit {
    @Column
    private Long noticeBoardIdx;

    @Column
    private String fileName;

    @Column
    private String savedFileName;

    @Column
    private String fileSize;

    @Builder
    public NoticeBoardAttachedFile(String createdBy, Long noticeBoardIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        this.noticeBoardIdx = noticeBoardIdx;
        this.fileName = fileName;
        this.savedFileName = savedFileName;
        this.fileSize = fileSize;
    }
}