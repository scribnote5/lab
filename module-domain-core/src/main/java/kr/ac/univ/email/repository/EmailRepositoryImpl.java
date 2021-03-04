package kr.ac.univ.email.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.email.domain.Email;
import kr.ac.univ.email.domain.QEmail;
import kr.ac.univ.email.domain.enums.ReceiverType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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

    public List<String> findAllByActiveStatus(ActiveStatus activeStatus) {
        QEmail email = QEmail.email;
        /*
         * select email
         *  WHERE receiver_type = 'receiver_type';
         */
        return queryFactory
                .select(email.emailAddress)
                .from(email)
                .where(email.activeStatus.eq(activeStatus))
                .fetch();
    }

    public List<String> findEmailByReceiverTypeAndActiveStatus(ReceiverType receiverType, ActiveStatus activeStatus) {
        QEmail email = QEmail.email;
        /*
         * select email
         *  WHERE receiver_type = 'receiver_type';
         */
        return queryFactory
                .select(email.emailAddress)
                .from(email)
                .where(email.receiverType.eq(receiverType).and(email.activeStatus.eq(activeStatus)))
                .fetch();
    }
}