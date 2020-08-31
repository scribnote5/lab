package kr.ac.univ.publication.repository;

import java.util.List;

import javax.transaction.Transactional;

import kr.ac.univ.publication.domain.Publication;
import kr.ac.univ.publication.domain.PublicationAttachedFile;
import kr.ac.univ.publication.domain.QPublicationAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository
@Transactional
public class PublicationAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public PublicationAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(PublicationAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<PublicationAttachedFile> findAttachedFileByPublicationIdx(Long publicationIdx) {
        QPublicationAttachedFile publicationAttachedFile = QPublicationAttachedFile.publicationAttachedFile;

        /* SELECT *
         *   FROM AttachedFile
         *  WHERE publicationIdx = 'publicationIdx'
         */
        return queryFactory
                .selectFrom(publicationAttachedFile)
                .where(publicationAttachedFile.publicationIdx.eq(publicationIdx))
                .fetch();
    }

    public Long deleteAttachedFileByPublicationIdx(Long publicationIdx) {
        QPublicationAttachedFile publicationAttachedFile = QPublicationAttachedFile.publicationAttachedFile;

        /* DELETE FROM AttachedFile
         *  WHERE publicationIdx = 'publicationIdx'
         */
        return queryFactory
                .delete(publicationAttachedFile)
                .where(publicationAttachedFile.publicationIdx.eq(publicationIdx))
                .execute();
    }
}