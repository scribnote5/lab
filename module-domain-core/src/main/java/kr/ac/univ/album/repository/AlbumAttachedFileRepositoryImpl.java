package kr.ac.univ.album.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.album.domain.AlbumAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static kr.ac.univ.album.domain.QAlbumAttachedFile.albumAttachedFile;

@Repository
@Transactional
public class AlbumAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public AlbumAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(AlbumAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<AlbumAttachedFile> findAttachedFileByAlbumIdx(Long albumIdx) {
         /* SELECT *
         *   FROM AttachedFile
         *  WHERE albumIdx = 'albumIdx'
         *  ORDER BY idx asc
         */
        return queryFactory
                .selectFrom(albumAttachedFile)
                .where(albumAttachedFile.albumIdx.eq(albumIdx))
                .orderBy(albumAttachedFile.idx.asc())
                .fetch();
    }

    public Long deleteAttachedFileByAlbumIdx(Long albumIdx) {
        /* DELETE FROM AttachedFile
         *  WHERE albumIdx = 'albumIdx'
         */
        return queryFactory
                .delete(albumAttachedFile)
                .where(albumAttachedFile.albumIdx.eq(albumIdx))
                .execute();
    }
}