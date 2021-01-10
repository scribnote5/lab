package kr.ac.univ.introductionImage.service;

import kr.ac.univ.introductionImage.domain.IntroductionImageAttachedFile;
import kr.ac.univ.introductionImage.dto.IntroductionImageDto;
import kr.ac.univ.introductionImage.dto.mapper.IntroductionImageMapper;
import kr.ac.univ.introductionImage.repository.IntroductionImageAttachedFileRepository;
import kr.ac.univ.introductionImage.repository.IntroductionImageAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class IntroductionImageAttachedFileService {
    private final IntroductionImageAttachedFileRepository introductionImageAttachedFileRepository;
    private final IntroductionImageAttachedFileRepositoryImpl introductionImageRepositoryImpl;

    public IntroductionImageAttachedFileService(IntroductionImageAttachedFileRepository introductionImageAttachedFileRepository, IntroductionImageAttachedFileRepositoryImpl introductionImageRepositoryImpl) {
        this.introductionImageAttachedFileRepository = introductionImageAttachedFileRepository;
        this.introductionImageRepositoryImpl = introductionImageRepositoryImpl;
    }

    public IntroductionImageDto findAttachedFileByIntroductionImageIdx(Long introductionImageIdx, IntroductionImageDto introductionImageDto) {

        return IntroductionImageMapper.INSTANCE.toDto(introductionImageDto, introductionImageRepositoryImpl.findAttachedFileByIntroductionImageIdx(introductionImageIdx));
    }

    public void insertAttachedFile(IntroductionImageAttachedFile attachedFile) {
        introductionImageAttachedFileRepository.save(attachedFile);
    }

    public IntroductionImageAttachedFile findAttachedFileByIdx(Long idx) {
        return introductionImageAttachedFileRepository.findById(idx).orElse(new IntroductionImageAttachedFile());
    }

    public IntroductionImageAttachedFile getAttachedFileByIdx(Long idx) {
        return introductionImageAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        introductionImageAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByIntroductionImageIdx(Long idx) {
        return introductionImageRepositoryImpl.deleteAttachedFileByIntroductionImageIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param introductionImageIdx
     * @param files
     */
    public void uploadAttachedFile(Long introductionImageIdx, String createdBy, MultipartFile[] files) throws Exception {
        IntroductionImageAttachedFile uploadFile = new IntroductionImageAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);

            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = IntroductionImageAttachedFile.builder()
                    .introductionImageIdx(introductionImageIdx)
                    .createdBy(createdBy)
                    .fileName(file.getOriginalFilename())
                    .savedFileName(savedFileName)
                    .fileSize(FileUtil.convertFileSize(file.getSize()))
                    .build();

            insertAttachedFile(uploadFile);
        }
    }

    /**
     * 첨부 파일 삭제
     *
     * @param deleteAttachedFileIdxList
     */
    public void deleteAttachedFile(List<Long> deleteAttachedFileIdxList) throws Exception {
        for (Long idx : deleteAttachedFileIdxList) {
            IntroductionImageAttachedFile attachedFile = findAttachedFileByIdx(idx);

            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

            deleteAttachedFileByIdx(attachedFile.getIdx());
        }
    }

    /**
     * 모든 첨부 파일 삭제
     *
     * @param introductionImageIdx
     */
    public void deleteAllAttachedFile(Long introductionImageIdx) throws Exception {
        List<IntroductionImageAttachedFile> attachedFileList = introductionImageRepositoryImpl.findAttachedFileByIntroductionImageIdx(introductionImageIdx);

        for (IntroductionImageAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByIntroductionImageIdx(introductionImageIdx);
    }
}