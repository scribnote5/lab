package kr.ac.univ.guestBook.domain;


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
public class GuestBookAttachedFile extends AttachedFileAudit {
    private Long guestBookIdx;

    @Builder
    public GuestBookAttachedFile(String createdBy, Long guestBookIdx, String fileName, String savedFileName, String fileSize) {
        setCreatedBy(createdBy);
        setFileName(fileName);
        setSavedFileName(savedFileName);
        setFileSize(fileSize);
        this.guestBookIdx = guestBookIdx;
    }
}
