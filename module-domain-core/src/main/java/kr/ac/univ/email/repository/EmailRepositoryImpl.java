package kr.ac.univ.email.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.email.domain.Email;
import kr.ac.univ.email.domain.QEmail;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class EmailRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public EmailRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Email.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QEmail email = QEmail.email;
        /*
         * UPDATE email
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(email)
                .set(email.views, email.views.add(1))
                .where(email.idx.eq(idx))
                .execute();
    }

}