package kr.ac.univ.setting.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.setting.domain.Setting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

}