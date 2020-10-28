package kr.ac.univ.project.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.project.domain.Project;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class ProjectListener {
    private final DataHistoryRepository dataHistoryRepository;

    public ProjectListener() {
        dataHistoryRepository = null;
    }

    public ProjectListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }


    @PostPersist
    public void postPersist(Project project) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(project.getCreatedDate())
                .lastModifiedDate(project.getLastModifiedDate())
                .createdBy(project.getCreatedBy())
                .lastModifiedBy(project.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(project.getIdx())
                .audClass("Project")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(project.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(Project project) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(project.getCreatedDate())
                .lastModifiedDate(project.getLastModifiedDate())
                .createdBy(project.getCreatedBy())
                .lastModifiedBy(project.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(project.getIdx())
                .audClass("Project")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(project.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(Project project) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(project.getCreatedDate())
                .lastModifiedDate(project.getLastModifiedDate())
                .createdBy(project.getCreatedBy())
                .lastModifiedBy(project.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(project.getIdx())
                .audClass("Project")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(project.getTitle()))
                .build());
    }
}