package kr.ac.univ.seminar.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.researchField.domain.ResearchField;
import kr.ac.univ.seminar.domain.enums.SeminarType;
import kr.ac.univ.seminar.listener.SeminarListener;
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
@EntityListeners(SeminarListener.class)
public class Seminar extends CommonAudit {
    @Column
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private SeminarType seminarType;

    @Column
    private LocalDateTime presentationDate;

    @Column
    private String place;

    @Column
    private String presenter;

    @Column
    private Long categoryIdx;

    @Column
    private String content;

    @Column
    private Long views = 0L;

    @Builder
    public Seminar(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title, SeminarType seminarType, ResearchField researchField, LocalDateTime presentationDate, String place, String presenter, Long categoryIdx, String content) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.seminarType = seminarType;
        this.presentationDate = presentationDate;
        this.place = place;
        this.presenter = presenter;
        this.categoryIdx = categoryIdx;
        this.content = content;
    }

    public void update(Seminar seminar) {
        setActiveStatus(seminar.getActiveStatus());
        this.title = seminar.getTitle();
        this.seminarType = seminar.getSeminarType();
        this.presentationDate = seminar.getPresentationDate();
        this.place = seminar.getPlace();
        this.presenter = seminar.getPresenter();
        this.categoryIdx = seminar.getCategoryIdx();
        this.content = seminar.getContent();
    }
}