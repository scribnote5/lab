package kr.ac.univ.publication.listener;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.publication.domain.Publication;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class PublicationListener {
    private final DataHistoryRepository dataHistoryRepository;

    public PublicationListener() {
        dataHistoryRepository = null;
    }

    public PublicationListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(Publication publication) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(publication.getCreatedDate())
                .lastModifiedDate(publication.getLastModifiedDate())
                .createdBy(publication.getCreatedBy())
                .lastModifiedBy(publication.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(publication.getIdx())
                .audClass("Publication")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(publication.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(Publication publication) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(publication.getCreatedDate())
                .lastModifiedDate(publication.getLastModifiedDate())
                .createdBy(publication.getCreatedBy())
                .lastModifiedBy(publication.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(publication.getIdx())
                .audClass("Publication")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(publication.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(Publication publication) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(publication.getCreatedDate())
                .lastModifiedDate(publication.getLastModifiedDate())
                .createdBy(publication.getCreatedBy())
                .lastModifiedBy(publication.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(publication.getIdx())
                .audClass("Publication")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(publication.getTitle()))
                .build());
    }
}