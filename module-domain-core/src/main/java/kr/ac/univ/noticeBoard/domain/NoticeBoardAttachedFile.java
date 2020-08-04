package kr.ac.univ.noticeBoard.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import kr.ac.univ.common.domain.AttachedFileAudit;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(AuditingEntityListener.class)
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
    public NoticeBoardAttachedFile(Long noticeBoardIdx, String fileName, String savedFileName, String fileSize) {
        this.fileName = fileName;
        this.noticeBoardIdx = noticeBoardIdx;
        this.savedFileName = savedFileName;
        this.fileSize = fileSize;
    }
}
