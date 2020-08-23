package kr.ac.univ.user.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import kr.ac.univ.user.domain.UserAttachedFile;
import kr.ac.univ.user.repository.UserAttachedFileRepository;
import kr.ac.univ.user.repository.UserAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserAttachedFileService {
    private final UserAttachedFileRepository userAttachedFileRepository;
    private final UserAttachedFileRepositoryImpl userAttachedFileRepositoryImpl;

    public UserAttachedFileService(UserAttachedFileRepository userAttachedFileRepository, UserAttachedFileRepositoryImpl userAttachedFileRepositoryImpl) {
        this.userAttachedFileRepository = userAttachedFileRepository;
        this.userAttachedFileRepositoryImpl = userAttachedFileRepositoryImpl;
    }

    public List<UserAttachedFile> findAttachedFileByUserIdx(Long userIdx) {
        return userAttachedFileRepositoryImpl.findAttachedFileByUserIdx(userIdx);
    }

    public void insertAttachedFile(UserAttachedFile userAttachedFile) {
        userAttachedFileRepository.save(userAttachedFile);
    }

    public UserAttachedFile findAttachedFileByIdx(Long idx) {
        return userAttachedFileRepository.findById(idx).orElse(new UserAttachedFile());
    }

    public UserAttachedFile getAttachedFileByIdx(Long idx) {
        return userAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        userAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByUserIdx(Long idx) {
        return userAttachedFileRepositoryImpl.deleteAttachedFileByUserIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param userIdx
     * @param files
     */
    public void uploadAttachedFile(Long userIdx, String createdBy, MultipartFile[] files) throws Exception {
        UserAttachedFile uploadFile = new UserAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = UserAttachedFile.builder()
                    .userIdx(userIdx)
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
     * @param userIdx
     */
    public void deleteAttachedFile(Long userIdx) throws Exception {
        List<UserAttachedFile> attachedFileList = findAttachedFileByUserIdx(userIdx);

        for (UserAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

        }

        deleteAttachedFileByUserIdx(userIdx);
    }
}
