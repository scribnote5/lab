package kr.ac.univ.album.repository;

import kr.ac.univ.album.domain.AlbumAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumAttachedFileRepository extends JpaRepository<AlbumAttachedFile, Long> {

}