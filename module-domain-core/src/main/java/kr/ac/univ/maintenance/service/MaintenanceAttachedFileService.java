package kr.ac.univ.maintenance.service;

import kr.ac.univ.maintenance.repository.MaintenanceAttachedFileRepository;
import kr.ac.univ.maintenance.repository.MaintenanceAttachedFileRepositoryImpl;
import kr.ac.univ.maintenance.domain.MaintenanceAttachedFile;
import kr.ac.univ.maintenance.dto.MaintenanceDto;
import kr.ac.univ.maintenance.dto.mapper.MaintenanceMapper;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class MaintenanceAttachedFileService {
    private final MaintenanceAttachedFileRepository maintenanceAttachedFileRepository;
    private final MaintenanceAttachedFileRepositoryImpl maintenanceAttachedFileRepositoryImpl;

    public MaintenanceAttachedFileService(MaintenanceAttachedFileRepository maintenanceAttachedFileRepository, MaintenanceAttachedFileRepositoryImpl maintenanceAttachedFileRepositoryImpl) {
        this.maintenanceAttachedFileRepository = maintenanceAttachedFileRepository;
        this.maintenanceAttachedFileRepositoryImpl = maintenanceAttachedFileRepositoryImpl;
    }

    public MaintenanceDto findAttachedFileByMaintenanceIdx(Long maintenanceIdx, MaintenanceDto maintenanceDto) {
        return MaintenanceMapper.INSTANCE.toDto(maintenanceDto, maintenanceAttachedFileRepositoryImpl.findAttachedFileByMaintenanceIdx(maintenanceIdx));
    }

    public void insertAttachedFile(MaintenanceAttachedFile attachedFile) {
        maintenanceAttachedFileRepository.save(attachedFile);
    }

    public MaintenanceAttachedFile findAttachedFileByIdx(Long idx) {
        return maintenanceAttachedFileRepository.findById(idx).orElse(new MaintenanceAttachedFile());
    }

    public MaintenanceAttachedFile getAttachedFileByIdx(Long idx) {
        return maintenanceAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        maintenanceAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByMaintenanceIdx(Long idx) {
        return maintenanceAttachedFileRepositoryImpl.deleteAttachedFileByMaintenanceIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param maintenanceIdx
     * @param files
     */
    public void uploadAttachedFile(Long maintenanceIdx, String createdBy, MultipartFile[] files) throws Exception {
        MaintenanceAttachedFile uploadFile = new MaintenanceAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);
            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = MaintenanceAttachedFile.builder()
                    .maintenanceIdx(maintenanceIdx)
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
            MaintenanceAttachedFile attachedFile = findAttachedFileByIdx(idx);

            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

            deleteAttachedFileByIdx(attachedFile.getIdx());
        }
    }

    /**
     * 모든 첨부 파일 삭제
     *
     * @param maintenanceIdx
     */
    public void deleteAllAttachedFile(Long maintenanceIdx) throws Exception {
        List<MaintenanceAttachedFile> attachedFileList = maintenanceAttachedFileRepositoryImpl.findAttachedFileByMaintenanceIdx(maintenanceIdx);

        for (MaintenanceAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByMaintenanceIdx(maintenanceIdx);
    }
}