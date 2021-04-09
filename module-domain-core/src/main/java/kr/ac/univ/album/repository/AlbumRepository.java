package kr.ac.univ.album.repository;

import kr.ac.univ.album.domain.Album;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findAllByTitleContaining(Pageable pageable, String title);

    Page<Album> findAllByHashTagsContaining(Pageable pageable, String hashTags);

    Page<Album> findAllByCreatedByContaining(Pageable pageable, String username);


    Page<Album> findAllByActiveStatusIs(Pageable pageable, ActiveStatus activeStatus);

    Page<Album> findAllByTitleContainingAndActiveStatusIs(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<Album> findAllByHashTagsContainingAndActiveStatusIs(Pageable pageable, String content, ActiveStatus activeStatus);

    Page<Album> findAllByCreatedByContainingAndActiveStatusIs(Pageable pageable, String username, ActiveStatus activeStatus);


    List<Album> findAllByActiveStatusIsAndMainPagePriorityGreaterThanEqualOrderByMainPagePriorityAsc(ActiveStatus activeStatus, Long mainPagePriority);

    Album findByMainPagePriorityIsAndActiveStatusIs(Long mainPagePriority, ActiveStatus activeStatus);


    List<Album> findAllByActiveStatusIsOrderByMainHashTagDescPhotoTakenDateDescIdxDesc(ActiveStatus activeStatus);
}