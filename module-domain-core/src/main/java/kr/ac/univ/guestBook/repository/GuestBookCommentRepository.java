package kr.ac.univ.guestBook.repository;

import kr.ac.univ.guestBook.domain.GuestBookComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestBookCommentRepository extends JpaRepository<GuestBookComment, Long> {
    List<GuestBookComment> findAllByGuestBookIdxOrderByCreatedDateDesc(Long guestBookIdx);

    void deleteAllByGuestBookIdx(Long guestBookIdx);
}