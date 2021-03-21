package kr.ac.univ.researchField.service;

import kr.ac.univ.researchField.domain.ResearchFieldAttachedFile;
import kr.ac.univ.researchField.dto.ResearchFieldDto;
import kr.ac.univ.researchField.dto.mapper.ResearchFieldMapper;
import kr.ac.univ.researchField.repository.ResearchFieldAttachedFileRepository;
import kr.ac.univ.researchField.repository.ResearchFieldAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ResearchFieldAttachedFileService {
    private final ResearchFieldAttachedFileRepository researchFieldAttachedFileRepository;
    private final ResearchFieldAttachedFileRepositoryImpl researchFieldAttachedFileRepositoryImpl;

    public ResearchFieldAttachedFileService(ResearchFieldAttachedFileRepository researchFieldAttachedFileRepository, ResearchFieldAttachedFileRepositoryImpl researchFieldAttachedFileRepositoryImpl) {
        this.researchFieldAttachedFileRepository = researchFieldAttachedFileRepository;
        this.researchFieldAttachedFileRepositoryImpl = researchFieldAttachedFileRepositoryImpl;
    }

    public ResearchFieldDto findAttachedFileByResearchFieldIdx(Long researchFieldIdx, ResearchFieldDto researchFieldDto) {

        return ResearchFieldMapper.INSTANCE.toDto(researchFieldDto, researchFieldAttachedFileRepositoryImpl.findAttachedFileByResearchFieldIdx(researchFieldIdx));
    }

    public void insertAttachedFile(ResearchFieldAttachedFile attachedFile) {
        researchFieldAttachedFileRepository.save(attachedFile);
    }

    public ResearchFieldAttachedFile findAttachedFileByIdx(Long idx) {
        return researchFieldAttachedFileRepository.findById(idx).orElse(new ResearchFieldAttachedFile());
    }

    public ResearchFieldAttachedFile getAttachedFileByIdx(Long idx) {
        return researchFieldAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        researchFieldAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByResearchFieldIdx(Long idx) {
        return researchFieldAttachedFileRepositoryImpl.deleteAttachedFileByResearchFieldIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param researchFieldIdx
     * @param files
     */
    public void uploadAttachedFile(Long researchFieldIdx, String createdBy, MultipartFile[] files) throws Exception {
        ResearchFieldAttachedFile uploadFile = new ResearchFieldAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);

            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = ResearchFieldAttachedFile.builder()
                    .researchFieldIdx(researchFieldIdx)
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
            ResearchFieldAttachedFile attachedFile = findAttachedFileByIdx(idx);

            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

            deleteAttachedFileByIdx(attachedFile.getIdx());
        }
    }

    /**
     * 모든 첨부 파일 삭제
     *
     * @param researchFieldIdx
     */
    public void deleteAllAttachedFile(Long researchFieldIdx) throws Exception {
        List<ResearchFieldAttachedFile> attachedFileList = researchFieldAttachedFileRepositoryImpl.findAttachedFileByResearchFieldIdx(researchFieldIdx);

        for (ResearchFieldAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByResearchFieldIdx(researchFieldIdx);
    }
}