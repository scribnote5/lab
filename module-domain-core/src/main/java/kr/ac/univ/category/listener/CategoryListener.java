package kr.ac.univ.category.listener;

import kr.ac.univ.category.domain.Category;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class CategoryListener {
    private final DataHistoryRepository dataHistoryRepository;

    public CategoryListener() {
        dataHistoryRepository = null;
    }

    public CategoryListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(Category category) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(category.getCreatedDate())
                .lastModifiedDate(category.getLastModifiedDate())
                .createdBy(category.getCreatedBy())
                .lastModifiedBy(category.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(category.getIdx())
                .audClass("Category")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(category.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(Category category) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(category.getCreatedDate())
                .lastModifiedDate(category.getLastModifiedDate())
                .createdBy(category.getCreatedBy())
                .lastModifiedBy(category.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(category.getIdx())
                .audClass("Category")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(category.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(Category category) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(category.getCreatedDate())
                .lastModifiedDate(category.getLastModifiedDate())
                .createdBy(category.getCreatedBy())
                .lastModifiedBy(category.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(category.getIdx())
                .audClass("Category")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(category.getTitle()))
                .build());
    }
}