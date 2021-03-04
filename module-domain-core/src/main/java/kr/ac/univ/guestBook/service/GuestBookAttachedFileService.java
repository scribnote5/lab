package kr.ac.univ.guestBook.service;

import kr.ac.univ.guestBook.domain.GuestBookAttachedFile;
import kr.ac.univ.guestBook.dto.GuestBookDto;
import kr.ac.univ.guestBook.dto.mapper.GuestBookMapper;
import kr.ac.univ.guestBook.repository.GuestBookAttachedFileRepository;
import kr.ac.univ.guestBook.repository.GuestBookAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class GuestBookAttachedFileService {
    private final GuestBookAttachedFileRepository guestBookAttachedFileRepository;
    private final GuestBookAttachedFileRepositoryImpl guestBookAttachedFileRepositoryImpl;

    public GuestBookAttachedFileService(GuestBookAttachedFileRepository guestBookAttachedFileRepository, GuestBookAttachedFileRepositoryImpl guestBookAttachedFileRepositoryImpl) {
        this.guestBookAttachedFileRepository = guestBookAttachedFileRepository;
        this.guestBookAttachedFileRepositoryImpl = guestBookAttachedFileRepositoryImpl;
    }

    public GuestBookDto findAttachedFileByGuestBookIdx(Long guestBookIdx, GuestBookDto guestBookDto) {
        return GuestBookMapper.INSTANCE.toDto(guestBookDto, guestBookAttachedFileRepositoryImpl.findAttachedFileByGuestBookIdx(guestBookIdx));
    }

    public void insertAttachedFile(GuestBookAttachedFile attachedFile) {
        guestBookAttachedFileRepository.save(attachedFile);
    }

    public GuestBookAttachedFile findAttachedFileByIdx(Long idx) {
        return guestBookAttachedFileRepository.findById(idx).orElse(new GuestBookAttachedFile());
    }

    public GuestBookAttachedFile getAttachedFileByIdx(Long idx) {
        return guestBookAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        guestBookAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByGuestBookIdx(Long idx) {
        return guestBookAttachedFileRepositoryImpl.deleteAttachedFileByGuestBookIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param guestBookIdx
     * @param files
     */
    public void uploadAttachedFile(Long guestBookIdx, String createdBy, MultipartFile[] files) throws Exception {
        GuestBookAttachedFile uploadFile = new GuestBookAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);
            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = GuestBookAttachedFile.builder()
                    .guestBookIdx(guestBookIdx)
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
            GuestBookAttachedFile attachedFile = findAttachedFileByIdx(idx);

            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

            deleteAttachedFileByIdx(attachedFile.getIdx());
        }
    }

    /**
     * 모든 첨부 파일 삭제
     *
     * @param guestBookIdx
     */
    public void deleteAllAttachedFile(Long guestBookIdx) throws Exception {
        List<GuestBookAttachedFile> attachedFileList = guestBookAttachedFileRepositoryImpl.findAttachedFileByGuestBookIdx(guestBookIdx);

        for (GuestBookAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByGuestBookIdx(guestBookIdx);
    }
}