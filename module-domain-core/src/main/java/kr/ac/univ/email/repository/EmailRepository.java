package kr.ac.univ.email.repository;

import kr.ac.univ.email.domain.Email;
import kr.ac.univ.email.domain.enums.ReceiverType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    Page<Email> findAllByEmailAddressContaining(Pageable pageable, String emailAddress);

    Page<Email> findAllByNoteContaining(Pageable pageable, String Note);

    Page<Email> findAllByCreatedByContaining(Pageable pageable, String username);
}