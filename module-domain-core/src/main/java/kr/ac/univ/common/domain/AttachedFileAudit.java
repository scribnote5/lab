package kr.ac.univ.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public abstract class AttachedFileAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @CreatedDate
    private LocalDateTime createdDate;

    @Column
    private String createdBy;

    @Column
    private String fileName;

    @Column
    private String savedFileName;

    @Column
    private String fileSize;
}