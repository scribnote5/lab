package kr.ac.univ.loginHistory.repository;

import kr.ac.univ.loginHistory.domain.LoginHistory;
import kr.ac.univ.loginHistory.domain.enums.AudLoginResultType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    Page<LoginHistory> findAllByAudMessageContaining(Pageable pageable, String audMessage);

    Page<LoginHistory> findAllByAudIpContaining(Pageable pageable, String audIp);

    Page<LoginHistory> findAllByAudLocationContaining(Pageable pageable, String audLocation);

    Page<LoginHistory> findAllByCreatedByContaining(Pageable pageable, String createdBy);

    Page<LoginHistory> findAllByAudLoginResultTypeIs(Pageable pageable, AudLoginResultType loginResultType);

    List<LoginHistory> findTop10ByOrderByIdxDesc();

    Long countAllByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
}