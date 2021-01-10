package kr.ac.univ.publication.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.publication.domain.Publication;
import kr.ac.univ.publication.domain.enums.PublicationType;
import kr.ac.univ.publication.domain.enums.PublishingArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    /* Show All */
    Page<Publication> findAllByTitleContaining(Pageable pageable, String keyword);

    Page<Publication> findAllByAuthorsContaining(Pageable pageable, String keyword);

    Page<Publication> findAllByPublishedInContaining(Pageable pageable, String keyword);

    /* publishingArea && publicationType */
    Page<Publication> findAllByTitleContainingAndPublicationTypeAndPublishingArea(Pageable pageable, String keyword, PublicationType publicationType, PublishingArea publishingArea);

    Page<Publication> findAllByAuthorsContainingAndPublicationTypeAndPublishingArea(Pageable pageable, String keyword, PublicationType publicationType, PublishingArea publishingArea);

    Page<Publication> findAllByPublishedInContainingAndPublicationTypeAndPublishingArea(Pageable pageable, String keyword, PublicationType publicationType, PublishingArea publishingArea);

    Page<Publication> findAllByPublicationTypeAndPublishingArea(Pageable pageable, PublicationType publicationType, PublishingArea publishingArea);

    Long countAllByActiveStatusIs(ActiveStatus activeStatus);
}