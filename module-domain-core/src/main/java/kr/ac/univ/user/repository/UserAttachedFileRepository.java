package kr.ac.univ.user.repository;

import kr.ac.univ.user.domain.UserAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttachedFileRepository extends JpaRepository<UserAttachedFile, Long> {

}