package kr.ac.univ.publication.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.univ.publication.domain.QPublication;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.publication.domain.Publication;
import kr.ac.univ.publication.domain.QPublication;
import kr.ac.univ.publication.domain.enums.PublicationType;
import kr.ac.univ.publication.domain.enums.PublishingArea;
import kr.ac.univ.publication.dto.PublicationSearchDto;
import kr.ac.univ.publication.dto.enums.PublicationSearchType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static kr.ac.univ.publication.domain.QPublication.publication;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional
public class PublicationRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public PublicationRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Publication.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QPublication publication = QPublication.publication;
        /*
         * UPDATE publication
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(publication)
                .set(publication.views, publication.views.add(1))
                .where(publication.idx.eq(idx))
                .execute();
    }

    public List<Publication> findTop10(Long lastIdx) {
        /* SELECT *
         *   FROM publication
         *  WHERE idx < 'lastIdx'
         *  LIMIT 10
         */
        return queryFactory
                .selectFrom(publication)
                .where(publication.idx.loe(lastIdx))
                .orderBy(publication.idx.desc())
                .limit(10)
                .fetch();
    }

    public Publication findMaxPublicationIdx() {
        /*
         * SELECT MAX(idx)
         * FROM publication
         */
        return queryFactory
                .selectFrom(publication)
                .orderBy(publication.idx.desc())
                .fetchFirst();
    }

    private BooleanExpression eqSearchType(PublicationSearchDto publicationSearchDto) {
        /*
         * SELECT *
         * FROM publication
         * WHERE searchType LIKE '%keyword%';
         */
        BooleanExpression result = null;

        switch (publicationSearchDto.getSearchType()) {
            case "TITLE":
                result = publication.title.contains(publicationSearchDto.getKeyword());
                break;
            case "AUTHORS":
                result = publication.authors.contains(publicationSearchDto.getKeyword());
                break;
            case "PUBLISHED_IN":
                result = publication.publishedIn.contains(publicationSearchDto.getKeyword());
                break;
            default:
                break;
        }

        return result;
    }

    private BooleanExpression eqPublicationSearchType(PublicationSearchDto publicationSearchDto) {
        /*
         * SELECT *
         *   FROM publication
         *  WHERE publishing_area = 'publishingArea'
         *    AND publication_type = 'publicationType'
         */
        BooleanExpression result = null;

        if (publicationSearchDto.getPublicationSearchType() == PublicationSearchType.INTERNATIONAL_JOURNAL) {
            result = publication.publishingArea.eq(PublishingArea.INTERNATIONAL)
                    .and(publication.publicationType.eq(PublicationType.JOURNAL)
                            .or(publication.publicationType.eq(PublicationType.JOURNAL_SCI))
                            .or(publication.publicationType.eq(PublicationType.JOURNAL_SCOPUS))
                    );
        } else if (publicationSearchDto.getPublicationSearchType() == PublicationSearchType.INTERNATIONAL_CONFERENCE) {
            result = publication.publishingArea.eq(PublishingArea.INTERNATIONAL)
                    .and(publication.publicationType.ne(PublicationType.JOURNAL)
                            .and(publication.publicationType.ne(PublicationType.JOURNAL_SCI))
                            .and(publication.publicationType.ne(PublicationType.JOURNAL_SCOPUS))
                    );
        } else if (publicationSearchDto.getPublicationSearchType() == PublicationSearchType.DOMESTIC_JOURNAL) {
            result = publication.publishingArea.eq(PublishingArea.DOMESTIC)
                    .and(publication.publicationType.eq(PublicationType.JOURNAL)
                            .or(publication.publicationType.eq(PublicationType.JOURNAL_KCI))
                    );
        } else if (publicationSearchDto.getPublicationSearchType() == PublicationSearchType.DOMESTIC_CONFERENCE) {
            result = publication.publishingArea.eq(PublishingArea.DOMESTIC)
                    .and(publication.publicationType.ne(PublicationType.JOURNAL)
                            .and(publication.publicationType.eq(PublicationType.JOURNAL_KCI))
                    );
        }

        return result;
    }

    public List<Publication> findTop10ByPublicationSearchDto(Long lastIdx, PublicationSearchDto publicationSearchDto) {
        QPublication publication = QPublication.publication;
        /*
         * SELECT *
         *   FROM publication
         *  WHERE idx <= lastIdx
         *    AND publishing_area = 'INTERNATIONAL'
         *    AND publication_type = 'JOURNAL'
         *    AND title LIKE '%'
         *  LIMIT 10;
         */
        return queryFactory
                .selectFrom(publication)
                .where(publication.idx.loe(lastIdx),
                        publication.activeStatus.eq(ActiveStatus.ACTIVE),
                        eqSearchType(publicationSearchDto),
                        eqPublicationSearchType(publicationSearchDto))
                .orderBy(publication.idx.desc())
                .limit(10)
                .fetch();
    }

}