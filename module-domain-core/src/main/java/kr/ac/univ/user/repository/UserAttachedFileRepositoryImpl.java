package kr.ac.univ.user.repository;


import java.util.List;

import javax.transaction.Transactional;

import kr.ac.univ.user.domain.UserAttachedFile;
import kr.ac.univ.user.domain.QUserAttachedFile;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional
public class UserAttachedFileRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public UserAttachedFileRepositoryImpl(JPAQueryFactory queryFactory) {
        super(UserAttachedFile.class);
        this.queryFactory = queryFactory;
    }

    public List<UserAttachedFile> findAttachedFileByUserIdx(Long userIdx) {
        QUserAttachedFile userAttachedFile = QUserAttachedFile.userAttachedFile;

        /* SELECT *
         *   FROM AttachedFile
         *  WHERE userIdx = 'userIdx'
         */
        return queryFactory
                .selectFrom(userAttachedFile)
                .where(userAttachedFile.userIdx.eq(userIdx))
                .fetch();
    }

    public Long deleteAttachedFileByUserIdx(Long userIdx) {
        QUserAttachedFile userAttachedFile = QUserAttachedFile.userAttachedFile;

        /* DELETE FROM AttachedFile
         *  WHERE userIdx = 'userIdx'
         */
        return queryFactory
                .delete(userAttachedFile)
                .where(userAttachedFile.userIdx.eq(userIdx))
                .execute();
    }
}