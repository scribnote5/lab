package kr.ac.univ.publication.domain;

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
public class PublicationAttachedFile extends AttachedFileAudit {
    @Column
    private Long publicationIdx;

    @Column
    private String fileName;

    @Column
    private String savedFileName;

    @Column
    private String fileSize;

    @Builder
    public PublicationAttachedFile(String createdBy, Long publicationIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        this.publicationIdx = publicationIdx;
        this.fileName = fileName;
        this.savedFileName = savedFileName;
        this.fileSize = fileSize;
    }
}