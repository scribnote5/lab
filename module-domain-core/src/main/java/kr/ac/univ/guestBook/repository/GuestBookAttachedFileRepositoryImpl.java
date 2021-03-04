package kr.ac.univ.guestBook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.guestBook.domain.GuestBookAttachedFile;
import kr.ac.univ.guestBook.domain.QGuestBookAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GuestBookAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public GuestBookAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(GuestBookAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<GuestBookAttachedFile> findAttachedFileByGuestBookIdx(Long guestBookIdx) {
        QGuestBookAttachedFile guestBookAttachedFile = QGuestBookAttachedFile.guestBookAttachedFile;

        /* SELECT *
         *   FROM AttachedFile
         *  WHERE guestBookIdx = 'guestBookIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(guestBookAttachedFile)
                .where(guestBookAttachedFile.guestBookIdx.eq(guestBookIdx))
                .orderBy(guestBookAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileByGuestBookIdx(Long guestBookIdx) {
        QGuestBookAttachedFile guestBookAttachedFile = QGuestBookAttachedFile.guestBookAttachedFile;

        /* DELETE FROM AttachedFile
         *  WHERE guestBookIdx = 'guestBookIdx'
         */
        return queryFactory
                .delete(guestBookAttachedFile)
                .where(guestBookAttachedFile.guestBookIdx.eq(guestBookIdx))
                .execute();
    }
}