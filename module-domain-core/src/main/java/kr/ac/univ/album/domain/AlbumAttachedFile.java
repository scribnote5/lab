package kr.ac.univ.album.domain;

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
public class AlbumAttachedFile extends AttachedFileAudit {
    private Long albumIdx;

    @Builder
    public AlbumAttachedFile(String createdBy, Long albumIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.albumIdx = albumIdx;
    }
}
