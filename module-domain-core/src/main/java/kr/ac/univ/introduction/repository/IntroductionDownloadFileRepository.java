//package kr.ac.univ.introduction.repository;
//
//import kr.ac.univ.introductionDownloadFile.domain.IntroductionDownloadFile;
//import kr.ac.univ.introductionDownloadFile.domain.enums.IntroductionDownloadFileType;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface IntroductionDownloadFileRepository extends JpaRepository<IntroductionDownloadFile, Long> {
//    Page<IntroductionDownloadFile> findAllByTitleContaining(Pageable pageable, String title);
//
//    Page<IntroductionDownloadFile> findAllByCreatedByContaining(Pageable pageable, String username);
//
//    Page<IntroductionDownloadFile> findAllByIntroductionDownloadFileTypeIs(Pageable pageable, String username, IntroductionDownloadFileType introductionDownloadFileType);
//}