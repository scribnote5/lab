package kr.ac.univ.guestBook.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.guestBook.domain.GuestBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {
    Page<GuestBook> findAllByTitleContaining(Pageable pageable, String title);

    Page<GuestBook> findAllByContentContaining(Pageable pageable, String content);

    Page<GuestBook> findAllByCreatedByContaining(Pageable pageable, String username);

    Page<GuestBook> findAllByActiveStatusIs(Pageable pageable, ActiveStatus activeStatus);

    Page<GuestBook> findAllByTitleContainingAndActiveStatusIs(Pageable pageable, String title, ActiveStatus activeStatus);

    Page<GuestBook> findAllByContentContainingAndActiveStatusIs(Pageable pageable, String content, ActiveStatus activeStatus);

    Page<GuestBook> findAllByCreatedByContainingAndActiveStatusIs(Pageable pageable, String username, ActiveStatus activeStatus);

    List<GuestBook> findTop10ByOrderByMainPagePriorityAscIdxDesc();

    List<GuestBook> findAllByActiveStatusIsAndMainPagePriorityLessThanEqualOrderByMainPagePriorityAsc(ActiveStatus activeStatus, Long mainPagePriority);

    Page<GuestBook> findAllByOrderByMainPagePriorityAsc(Pageable pageable);

    Page<GuestBook> findAllByActiveStatusIsOrderByMainPagePriorityAsc(Pageable pageable, ActiveStatus activeStatus);
}