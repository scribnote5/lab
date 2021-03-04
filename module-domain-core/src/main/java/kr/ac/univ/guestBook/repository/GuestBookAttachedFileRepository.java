package kr.ac.univ.guestBook.repository;

import kr.ac.univ.guestBook.domain.GuestBookAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestBookAttachedFileRepository extends JpaRepository<GuestBookAttachedFile, Long> {

}