package kr.ac.univ.event.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.event.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByOrderByStartDateDesc(Pageable pageable);

    Page<Event> findAllByTitleContainingOrderByStartDateDesc(Pageable pageable, String title);

    Page<Event> findAllByContentContainingOrderByStartDateDesc(Pageable pageable, String content);

    Page<Event> findAllByCreatedByContainingOrderByStartDateDesc(Pageable pageable, String username);


    Page<Event> findAllByActiveStatusIsOrderByStartDateDesc(Pageable pageable, ActiveStatus activeStatus);

    Page<Event> findAllByTitleContainingAndActiveStatusIsOrderByStartDateDesc(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<Event> findAllByContentContainingAndActiveStatusIsOrderByStartDateDesc(Pageable pageable, String content, ActiveStatus activeStatus);

    Page<Event> findAllByCreatedByContainingAndActiveStatusIsOrderByStartDateDesc(Pageable pageable, String username, ActiveStatus activeStatus);

    List<Event> findTop4ByStartDateAfterOrEndDateAfterOrderByStartDate(LocalDate currentDate, LocalDate currentDate2);
}