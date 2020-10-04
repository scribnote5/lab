package kr.ac.univ.learnMore.service;

import kr.ac.univ.learnMore.domain.LearnMoreAttachedFile;
import kr.ac.univ.learnMore.dto.LearnMoreDto;
import kr.ac.univ.learnMore.dto.mapper.LearnMoreMapper;
import kr.ac.univ.learnMore.repository.LearnMoreAttachedFileRepository;
import kr.ac.univ.learnMore.repository.LearnMoreAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class LearnMoreAttachedFileService {
    private final LearnMoreAttachedFileRepository learnMoreAttachedFileRepository;
    private final LearnMoreAttachedFileRepositoryImpl learnMoreAttachedFileRepositoryImpl;

    public LearnMoreAttachedFileService(LearnMoreAttachedFileRepository learnMoreAttachedFileRepository, LearnMoreAttachedFileRepositoryImpl learnMoreAttachedFileRepositoryImpl) {
        this.learnMoreAttachedFileRepository = learnMoreAttachedFileRepository;
        this.learnMoreAttachedFileRepositoryImpl = learnMoreAttachedFileRepositoryImpl;
    }

    public LearnMoreDto findAttachedFileByLearnMoreIdx(Long learnMoreIdx, LearnMoreDto learnMoreDto) {

        return LearnMoreMapper.INSTANCE.toDto(learnMoreDto, learnMoreAttachedFileRepositoryImpl.findAttachedFileByLearnMoreIdx(learnMoreIdx));
    }

    public void insertAttachedFile(LearnMoreAttachedFile attachedFile) {
        learnMoreAttachedFileRepository.save(attachedFile);
    }

    public LearnMoreAttachedFile findAttachedFileByIdx(Long idx) {
        return learnMoreAttachedFileRepository.findById(idx).orElse(new LearnMoreAttachedFile());
    }

    public LearnMoreAttachedFile getAttachedFileByIdx(Long idx) {
        return learnMoreAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        learnMoreAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByLearnMoreIdx(Long idx) {
        return learnMoreAttachedFileRepositoryImpl.deleteAttachedFileByLearnMoreIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param learnMoreIdx
     * @param files
     */
    public void uploadAttachedFile(Long learnMoreIdx, String createdBy, MultipartFile[] files) throws Exception {
        LearnMoreAttachedFile uploadFile = new LearnMoreAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);

            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = LearnMoreAttachedFile.builder()
                    .learnMoreIdx(learnMoreIdx)
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
            LearnMoreAttachedFile attachedFile = findAttachedFileByIdx(idx);

            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

            deleteAttachedFileByIdx(attachedFile.getIdx());
        }
    }

    /**
     * 모든 첨부 파일 삭제
     *
     * @param learnMoreIdx
     */
    public void deleteAllAttachedFile(Long learnMoreIdx) throws Exception {
        List<LearnMoreAttachedFile> attachedFileList = learnMoreAttachedFileRepositoryImpl.findAttachedFileByLearnMoreIdx(learnMoreIdx);

        for (LearnMoreAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByLearnMoreIdx(learnMoreIdx);
    }
}