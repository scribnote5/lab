package kr.ac.univ.maintenance.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.maintenance.domain.Maintenance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    Page<Maintenance> findAllByTitleContaining(Pageable pageable, String title);

    Page<Maintenance> findAllByContentContaining(Pageable pageable, String content);

    Page<Maintenance> findAllByCreatedByContaining(Pageable pageable, String username);

    Page<Maintenance> findAllByActiveStatusIs(Pageable pageable, ActiveStatus activeStatus);

    Page<Maintenance> findAllByTitleContainingAndActiveStatusIs(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<Maintenance> findAllByContentContainingAndActiveStatusIs(Pageable pageable, String content, ActiveStatus activeStatus);

    Page<Maintenance> findAllByCreatedByContainingAndActiveStatusIs(Pageable pageable, String username, ActiveStatus activeStatus);
}