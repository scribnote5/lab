package kr.ac.univ.setting.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.univ.setting.domain.Setting;
import kr.ac.univ.setting.domain.QSetting;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class SettingRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public SettingRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Setting.class);
        this.queryFactory = queryFactory;
    }

    public long updateViewsByIdx(Long idx) {
        QSetting setting = QSetting.setting;
        /*
         * UPDATE setting
         *    SET views = views + 1
         *  WHERE id = 'id';
         */
        return queryFactory
                .update(setting)
                .set(setting.views, setting.views.add(1))
                .where(setting.idx.eq(idx))
                .execute();
    }
}