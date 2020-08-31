package kr.ac.univ.publication.domain;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.publication.domain.enums.PublicationType;
import kr.ac.univ.publication.domain.enums.PublishingArea;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
public class Publication extends CommonAudit {
    @Column
    private String title;

    @Column
    private String authors;

    @Column
    @Enumerated(EnumType.STRING)
    private PublicationType publicationType;

    @Column
    @Enumerated(EnumType.STRING)
    private PublishingArea publishingArea;

    @Column
    private String publishedIn;

    @Column
    private String impactFactor;

    @Column
    private LocalDate publishedDate;

    @Column
    private String pages;

    @Column
    private String volume;

    @Column
    private String number;

    @Column
    private String doi;

    @Column
    private String uri;

    @Column
    private String isbnIssn;

    @Column
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