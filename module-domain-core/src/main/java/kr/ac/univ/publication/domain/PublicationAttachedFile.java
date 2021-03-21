package kr.ac.univ.publication.domain;

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
public class PublicationAttachedFile extends AttachedFileAudit {
    private Long publicationIdx;

    private String fileName;

    private String savedFileName;

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