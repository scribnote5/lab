package kr.ac.univ.seminar.repository;

import kr.ac.univ.seminar.domain.SeminarAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeminarAttachedFileRepository extends JpaRepository<SeminarAttachedFile, Long> {

}