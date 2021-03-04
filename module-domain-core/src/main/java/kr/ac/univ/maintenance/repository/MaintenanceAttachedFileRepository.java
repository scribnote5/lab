package kr.ac.univ.maintenance.repository;

import kr.ac.univ.maintenance.domain.MaintenanceAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceAttachedFileRepository extends JpaRepository<MaintenanceAttachedFile, Long> {

}