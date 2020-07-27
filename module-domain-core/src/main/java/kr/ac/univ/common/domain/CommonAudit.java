package kr.ac.univ.common.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.ToString;

@MappedSuperclass
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idx;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private String createdBy;

    private String lastModifiedBy;
}