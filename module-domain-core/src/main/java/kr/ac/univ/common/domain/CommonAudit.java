package kr.ac.univ.common.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@ToString
public abstract class CommonAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idx;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private String createdBy;

    private String lastModifiedBy;
}