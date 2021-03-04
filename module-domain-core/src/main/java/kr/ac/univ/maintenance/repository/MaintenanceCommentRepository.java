package kr.ac.univ.maintenance.repository;

import kr.ac.univ.maintenance.domain.MaintenanceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceCommentRepository extends JpaRepository<MaintenanceComment, Long> {
    List<MaintenanceComment> findAllByMaintenanceIdxOrderByCreatedDateDesc(Long maintenanceIdx);

    void deleteAllByMaintenanceIdx(Long maintenanceIdx);
}