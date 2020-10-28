package kr.ac.univ.dataHistory.repository;

import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataHistoryRepository extends JpaRepository<DataHistory, Long> {
    Page<DataHistory> findAllByAudMessageContaining(Pageable pageable, String audMessage);

    Page<DataHistory> findAllByAudClassContaining(Pageable pageable, String audClass);

    Page<DataHistory> findAllByAudTypeIs(Pageable pageable, AudType audType);

    Page<DataHistory> findAllByCreatedByContaining(Pageable pageable, String createdBy);

    List<DataHistory> findTop10ByOrderByIdxDesc();

    Long countByAudTypeIs(AudType audType);

    Long countAllByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
}