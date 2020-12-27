package kr.ac.univ.learnMoreRead.service;

import kr.ac.univ.learnMoreRead.domain.LearnMoreReadAttachedFile;
import kr.ac.univ.learnMoreRead.dto.LearnMoreReadDto;
import kr.ac.univ.learnMoreRead.dto.mapper.LearnMoreReadMapper;
import kr.ac.univ.learnMoreRead.repository.LearnMoreReadAttachedFileRepository;
import kr.ac.univ.learnMoreRead.repository.LearnMoreReadAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class LearnMoreReadAttachedFileService {
    private final LearnMoreReadAttachedFileRepository learnMoreReadAttachedFileRepository;
    private final LearnMoreReadAttachedFileRepositoryImpl learnMoreReadAttachedFileRepositoryImpl;

    public LearnMoreReadAttachedFileService(LearnMoreReadAttachedFileRepository learnMoreReadAttachedFileRepository, LearnMoreReadAttachedFileRepositoryImpl learnMoreReadAttachedFileRepositoryImpl) {
        this.learnMoreReadAttachedFileRepository = learnMoreReadAttachedFileRepository;
        this.learnMoreReadAttachedFileRepositoryImpl = learnMoreReadAttachedFileRepositoryImpl;
    }

    public LearnMoreReadDto findAttachedFileByLearnMoreIdx(Long learnMoreIdx, LearnMoreReadDto learnMoreDto) {
        return LearnMoreReadMapper.INSTANCE.toDto(learnMoreDto, learnMoreReadAttachedFileRepositoryImpl.findAttachedFileByLearnMoreReadIdx(learnMoreIdx));
    }

    public void insertAttachedFile(LearnMoreReadAttachedFile attachedFile) {
        learnMoreReadAttachedFileRepository.save(attachedFile);
    }

    public LearnMoreReadAttachedFile findAttachedFileByIdx(Long idx) {
        return learnMoreReadAttachedFileRepository.findById(idx).orElse(new LearnMoreReadAttachedFile());
    }

    public LearnMoreReadAttachedFile getAttachedFileByIdx(Long idx) {
        return learnMoreReadAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        learnMoreReadAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByLearnMoreIdx(Long idx) {
        return learnMoreReadAttachedFileRepositoryImpl.deleteAttachedFileByLearnMoreReadIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param learnMoreIdx
     * @param files
     */
    public void uploadAttachedFile(Long learnMoreIdx, String createdBy, MultipartFile[] files) throws Exception {
        LearnMoreReadAttachedFile uploadFile = new LearnMoreReadAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);

            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = LearnMoreReadAttachedFile.builder()
                    .learnMoreReadIdx(learnMoreIdx)
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
            LearnMoreReadAttachedFile attachedFile = findAttachedFileByIdx(idx);

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
        List<LearnMoreReadAttachedFile> attachedFileList = learnMoreReadAttachedFileRepositoryImpl.findAttachedFileByLearnMoreReadIdx(learnMoreIdx);

        for (LearnMoreReadAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByLearnMoreIdx(learnMoreIdx);
    }
}