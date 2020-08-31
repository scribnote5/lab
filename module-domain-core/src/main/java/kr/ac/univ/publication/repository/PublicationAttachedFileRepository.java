package kr.ac.univ.publication.repository;

import kr.ac.univ.publication.domain.PublicationAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PublicationAttachedFileRepository extends JpaRepository<PublicationAttachedFile, Long> {

}