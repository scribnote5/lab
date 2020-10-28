package kr.ac.univ.album.listener;

import kr.ac.univ.album.domain.Album;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import kr.ac.univ.dataHistory.repository.DataHistoryRepository;
import kr.ac.univ.util.AudMessageUtil;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class AlbumListener {
    private final DataHistoryRepository dataHistoryRepository;

    public AlbumListener() {
        dataHistoryRepository = null;
    }

    public AlbumListener(DataHistoryRepository dataHistoryRepository) {
        this.dataHistoryRepository = dataHistoryRepository;
    }

    @PostPersist
    public void postPersist(Album album) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(album.getCreatedDate())
                .lastModifiedDate(album.getLastModifiedDate())
                .createdBy(album.getCreatedBy())
                .lastModifiedBy(album.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(album.getIdx())
                .audClass("Album")
                .audType(AudType.INSERT)
                .audMessage(AudMessageUtil.getInsertAudMessage(album.getTitle()))
                .build());
    }

    @PostUpdate
    public void postUpdate(Album album) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(album.getCreatedDate())
                .lastModifiedDate(album.getLastModifiedDate())
                .createdBy(album.getCreatedBy())
                .lastModifiedBy(album.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(album.getIdx())
                .audClass("Album")
                .audType(AudType.UPDATE)
                .audMessage(AudMessageUtil.getUpdateAudMessage(album.getTitle()))
                .build());
    }

    @PostRemove
    public void postRemove(Album album) {
        dataHistoryRepository.save(DataHistory.builder()
                .createdDate(album.getCreatedDate())
                .lastModifiedDate(album.getLastModifiedDate())
                .createdBy(album.getCreatedBy())
                .lastModifiedBy(album.getLastModifiedBy())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(album.getIdx())
                .audClass("Album")
                .audType(AudType.DELETE)
                .audMessage(AudMessageUtil.getDeleteAudMessage(album.getTitle()))
                .build());
    }
}