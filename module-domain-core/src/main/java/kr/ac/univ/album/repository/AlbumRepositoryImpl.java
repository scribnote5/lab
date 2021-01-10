package kr.ac.univ.album.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.album.domain.Album;
import kr.ac.univ.album.domain.QAlbum;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AlbumRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public AlbumRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Album.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QAlbum album = QAlbum.album;
        /*
         * UPDATE album
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(album)
                .set(album.views, album.views.add(1))
                .where(album.idx.eq(idx))
                .execute();
    }
}