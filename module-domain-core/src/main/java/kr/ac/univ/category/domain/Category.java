package kr.ac.univ.category.domain;

import kr.ac.univ.category.listener.CategoryListener;
import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(CategoryListener.class)
public class Category extends CommonAudit {
    private String title;

    @Builder
    public Category(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String title) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
    }

    public void update(Category introduction) {
        setActiveStatus(introduction.getActiveStatus());
        this.title = introduction.getTitle();
    }
}