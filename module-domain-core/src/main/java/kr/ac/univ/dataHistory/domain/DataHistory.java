package kr.ac.univ.dataHistory.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
public class DataHistory extends CommonAudit {
    @Column
    private Long audIdx;

    @Column
    private String audClass;

    @Column
    @Enumerated(EnumType.STRING)
    private AudType audType;

    @Column
    private String audMessage;

    @Column
    private String audSubMessage;

    @Builder
    public DataHistory(Long idx, LocalDateTime createdDate, LocalDateTime lastModifiedDate, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, Long audIdx, AudType audType, String audClass, String audMessage,
                       String audSubMessage) {
        setIdx(idx);
        setCreatedDate(createdDate);
        setLastModifiedDate(lastModifiedDate);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.audIdx = audIdx;
        this.audClass = audClass;
        this.audType = audType;
        this.audMessage = audMessage;
        this.audSubMessage = audSubMessage;
    }

    public void update(DataHistory dataHistory) {
        setActiveStatus(dataHistory.getActiveStatus());
        this.audIdx = dataHistory.getAudIdx();
        this.audClass = dataHistory.getAudClass();
        this.audType = dataHistory.getAudType();
        this.audMessage = dataHistory.getAudMessage();
        this.audSubMessage = dataHistory.getAudSubMessage();
    }
}