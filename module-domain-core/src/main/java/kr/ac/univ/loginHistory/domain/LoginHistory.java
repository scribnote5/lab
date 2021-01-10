package kr.ac.univ.loginHistory.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.loginHistory.domain.enums.AudLoginResultType;
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
public class LoginHistory extends CommonAudit {
    private Long audIdx;

    private String audIp;

    private String audLocation;

    private String audMessage;

    private String audSubMessage;

    @Enumerated(EnumType.STRING)
    private AudLoginResultType audLoginResultType;

    @Builder
    public LoginHistory(Long idx, LocalDateTime createdDate, LocalDateTime lastModifiedDate, String createdBy, String lastModifiedBy, ActiveStatus activeStatus,
                        Long audIdx, String audIp, String audLocation, String audMessage, String audSubMessage, AudLoginResultType audLoginResultType) {
        setIdx(idx);
        setCreatedDate(createdDate);
        setLastModifiedDate(lastModifiedDate);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.audIdx = audIdx;
        this.audIp = audIp;
        this.audLocation = audLocation;
        this.audMessage = audMessage;
        this.audSubMessage = audSubMessage;
        this.audLoginResultType = audLoginResultType;
    }

    public void update(LoginHistory loginHistory) {
        setActiveStatus(loginHistory.getActiveStatus());
        this.audIdx = loginHistory.getAudIdx();
        this.audIp = loginHistory.getAudIp();
        this.audLocation = loginHistory.getAudLocation();
        this.audMessage = loginHistory.getAudMessage();
        this.audSubMessage = loginHistory.getAudSubMessage();
        this.audLoginResultType = loginHistory.getAudLoginResultType();
    }
}