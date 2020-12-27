package kr.ac.univ.learnMoreVideo.service;

import kr.ac.univ.learnMoreVideo.domain.LearnMoreVideoAttachedFile;
import kr.ac.univ.learnMoreVideo.dto.LearnMoreVideoDto;
import kr.ac.univ.learnMoreVideo.dto.mapper.LearnMoreVideoMapper;
import kr.ac.univ.learnMoreVideo.repository.LearnMoreVideoAttachedFileRepository;
import kr.ac.univ.learnMoreVideo.repository.LearnMoreVideoAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class LearnMoreVideoAttachedFileService {
    private final LearnMoreVideoAttachedFileRepository learnMoreVideoAttachedFileRepository;
    private final LearnMoreVideoAttachedFileRepositoryImpl learnMoreVideoAttachedFileRepositoryImpl;

    public LearnMoreVideoAttachedFileService(LearnMoreVideoAttachedFileRepository learnMoreVideoAttachedFileRepository, LearnMoreVideoAttachedFileRepositoryImpl learnMoreVideoAttachedFileRepositoryImpl) {
        this.learnMoreVideoAttachedFileRepository = learnMoreVideoAttachedFileRepository;
        this.learnMoreVideoAttachedFileRepositoryImpl = learnMoreVideoAttachedFileRepositoryImpl;
    }

    public LearnMoreVideoDto findAttachedFileByLearnMoreIdx(Long learnMoreIdx, LearnMoreVideoDto learnMoreDto) {
        return LearnMoreVideoMapper.INSTANCE.toDto(learnMoreDto, learnMoreVideoAttachedFileRepositoryImpl.findAttachedFileByLearnMoreVideoIdx(learnMoreIdx));
    }

    public void insertAttachedFile(LearnMoreVideoAttachedFile attachedFile) {
        learnMoreVideoAttachedFileRepository.save(attachedFile);
    }

    public LearnMoreVideoAttachedFile findAttachedFileByIdx(Long idx) {
        return learnMoreVideoAttachedFileRepository.findById(idx).orElse(new LearnMoreVideoAttachedFile());
    }

    public LearnMoreVideoAttachedFile getAttachedFileByIdx(Long idx) {
        return learnMoreVideoAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        learnMoreVideoAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByLearnMoreIdx(Long idx) {
        return learnMoreVideoAttachedFileRepositoryImpl.deleteAttachedFileByLearnMoreVideoIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param learnMoreIdx
     * @param files
     */
    public void uploadAttachedFile(Long learnMoreIdx, String createdBy, MultipartFile[] files) throws Exception {
        LearnMoreVideoAttachedFile uploadFile = new LearnMoreVideoAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);

            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = LearnMoreVideoAttachedFile.builder()
                    .learnMoreVideoIdx(learnMoreIdx)
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
            LearnMoreVideoAttachedFile attachedFile = findAttachedFileByIdx(idx);

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
        List<LearnMoreVideoAttachedFile> attachedFileList = learnMoreVideoAttachedFileRepositoryImpl.findAttachedFileByLearnMoreVideoIdx(learnMoreIdx);

        for (LearnMoreVideoAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByLearnMoreIdx(learnMoreIdx);
    }
}