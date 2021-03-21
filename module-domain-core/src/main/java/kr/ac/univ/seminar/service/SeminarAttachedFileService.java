package kr.ac.univ.seminar.service;

import kr.ac.univ.seminar.domain.SeminarAttachedFile;
import kr.ac.univ.seminar.dto.SeminarDto;
import kr.ac.univ.seminar.dto.mapper.SeminarMapper;
import kr.ac.univ.seminar.repository.SeminarAttachedFileRepository;
import kr.ac.univ.seminar.repository.SeminarAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class SeminarAttachedFileService {
    private final SeminarAttachedFileRepository seminarAttachedFileRepository;
    private final SeminarAttachedFileRepositoryImpl seminarAttachedFileRepositoryImpl;

    public SeminarAttachedFileService(SeminarAttachedFileRepository seminarAttachedFileRepository, SeminarAttachedFileRepositoryImpl seminarAttachedFileRepositoryImpl) {
        this.seminarAttachedFileRepository = seminarAttachedFileRepository;
        this.seminarAttachedFileRepositoryImpl = seminarAttachedFileRepositoryImpl;
    }

    public SeminarDto findAttachedFileBySeminarIdx(Long seminarIdx, SeminarDto seminarDto) {

        return SeminarMapper.INSTANCE.toDto(seminarDto, seminarAttachedFileRepositoryImpl.findAttachedFileBySeminarIdx(seminarIdx));
    }

    public void insertAttachedFile(SeminarAttachedFile attachedFile) {
        seminarAttachedFileRepository.save(attachedFile);
    }

    public SeminarAttachedFile findAttachedFileByIdx(Long idx) {
        return seminarAttachedFileRepository.findById(idx).orElse(new SeminarAttachedFile());
    }

    public SeminarAttachedFile getAttachedFileByIdx(Long idx) {
        return seminarAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        seminarAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileBySeminarIdx(Long idx) {
        return seminarAttachedFileRepositoryImpl.deleteAttachedFileBySeminarIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param seminarIdx
     * @param files
     */
    public void uploadAttachedFile(Long seminarIdx, String createdBy, MultipartFile[] files) throws Exception {
        SeminarAttachedFile uploadFile;

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);
            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = SeminarAttachedFile.builder()
                    .seminarIdx(seminarIdx)
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
            SeminarAttachedFile attachedFile = findAttachedFileByIdx(idx);

            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

            deleteAttachedFileByIdx(attachedFile.getIdx());
        }
    }

    /**
     * 모든 첨부 파일 삭제
     *
     * @param seminarIdx
     */
    public void deleteAllAttachedFile(Long seminarIdx) throws Exception {
        List<SeminarAttachedFile> attachedFileList = seminarAttachedFileRepositoryImpl.findAttachedFileBySeminarIdx(seminarIdx);

        for (SeminarAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileBySeminarIdx(seminarIdx);
    }
}