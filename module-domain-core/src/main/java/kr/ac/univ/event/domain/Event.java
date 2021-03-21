package kr.ac.univ.event.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.event.listener.EventListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(EventListener.class)
public class Event extends CommonAudit {
    private String title;

    private String content;

    private String place;

    private LocalDate startDate;

    private LocalDate endDate;

    @Builder
    public Event(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus,
                 String title, String content, String place, LocalDate startDate, LocalDate endDate) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.content = content;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(Event event) {
        setActiveStatus(event.getActiveStatus());
        this.title = event.getTitle();
        this.content = event.getContent();
        this.place = event.getPlace();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }
}