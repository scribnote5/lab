package kr.ac.univ.common.domain;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class AttachedFileAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idx;

    private LocalDateTime createdDate;

    private String createdBy;
}