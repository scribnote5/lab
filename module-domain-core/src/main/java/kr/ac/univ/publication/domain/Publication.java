package kr.ac.univ.publication.domain;


import java.time.LocalDate;

import javax.persistence.*;


import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.publication.domain.enums.PublicationType;
import kr.ac.univ.publication.domain.enums.PublishingArea;
import kr.ac.univ.publication.listener.PublicationListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(PublicationListener.class)
public class Publication extends CommonAudit {
    private String title;

    private String authors;

    @Enumerated(EnumType.STRING)
    private PublicationType publicationType;

    @Enumerated(EnumType.STRING)
    private PublishingArea publishingArea;

    private String publishedIn;

    private String impactFactor;

    private LocalDate publishedDate;

    private String pages;

    private String volume;

    private String number;

    private String doi;

    private String uri;

    private String isbnIssn;

    private String remark;

    @Builder
    public Publication(Long idx, String createdBy, String lastModifiedBy, String title, ActiveStatus activeStatus,
                       String authors, PublicationType publicationType, PublishingArea publishingArea,
                       String publishedIn, String impactFactor, LocalDate publishedDate, String pages, String volume,
                       String number, String doi, String uri, String isbnIssn, String remark) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.title = title;
        this.authors = authors;
        this.publicationType = publicationType;
        this.publishingArea = publishingArea;
        this.publishedIn = publishedIn;
        this.impactFactor = impactFactor;
        this.publishedDate = publishedDate;
        this.pages = pages;
        this.volume = volume;
        this.number = number;
        this.doi = doi;
        this.uri = uri;
        this.isbnIssn = isbnIssn;
        this.remark = remark;
    }

    public void update(Publication publication) {
        setActiveStatus(publication.getActiveStatus());
        this.title = publication.getTitle();
        this.authors = publication.getAuthors();
        this.publicationType = publication.getPublicationType();
        this.publishingArea = publication.getPublishingArea();
        this.publishedIn = publication.getPublishedIn();
        this.impactFactor = publication.getImpactFactor();
        this.publishedDate = publication.getPublishedDate();
        this.pages = publication.getPages();
        this.volume = publication.getVolume();
        this.number = publication.getNumber();
        this.doi = publication.getDoi();
        this.uri = publication.getUri();
        this.isbnIssn = publication.getIsbnIssn();
        this.remark = publication.getRemark();
    }

}