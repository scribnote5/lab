package kr.ac.univ.noticeBoard.service;

import kr.ac.univ.noticeBoard.domain.NoticeBoardAttachedFile;
import kr.ac.univ.noticeBoard.dto.NoticeBoardDto;
import kr.ac.univ.noticeBoard.dto.mapper.NoticeBoardMapper;
import kr.ac.univ.noticeBoard.repository.NoticeBoardAttachedFileRepository;
import kr.ac.univ.noticeBoard.repository.NoticeBoardAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class NoticeBoardAttachedFileService {
    private final NoticeBoardAttachedFileRepository noticeBoardAttachedFileRepository;
    private final NoticeBoardAttachedFileRepositoryImpl noticeBoardAttachedFileRepositoryImpl;

    public NoticeBoardAttachedFileService(NoticeBoardAttachedFileRepository noticeBoardAttachedFileRepository, NoticeBoardAttachedFileRepositoryImpl noticeBoardAttachedFileRepositoryImpl) {
        this.noticeBoardAttachedFileRepository = noticeBoardAttachedFileRepository;
        this.noticeBoardAttachedFileRepositoryImpl = noticeBoardAttachedFileRepositoryImpl;
    }

    public NoticeBoardDto findAttachedFileByNoticeBoardIdx(Long noticeBoardIdx, NoticeBoardDto noticeBoardDto) {
        return NoticeBoardMapper.INSTANCE.toDto(noticeBoardDto, noticeBoardAttachedFileRepositoryImpl.findAttachedFileByNoticeBoardIdx(noticeBoardIdx));
    }

    public void insertAttachedFile(NoticeBoardAttachedFile attachedFile) {
        noticeBoardAttachedFileRepository.save(attachedFile);
    }

    public NoticeBoardAttachedFile findAttachedFileByIdx(Long idx) {
        return noticeBoardAttachedFileRepository.findById(idx).orElse(new NoticeBoardAttachedFile());
    }

    public NoticeBoardAttachedFile getAttachedFileByIdx(Long idx) {
        return noticeBoardAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        noticeBoardAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByNoticeBoardIdx(Long idx) {
        return noticeBoardAttachedFileRepositoryImpl.deleteAttachedFileByNoticeBoardIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param noticeBoardIdx
     * @param files
     */
    public void uploadAttachedFile(Long noticeBoardIdx, String createdBy, MultipartFile[] files) throws Exception {
        NoticeBoardAttachedFile uploadFile = new NoticeBoardAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);
            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = NoticeBoardAttachedFile.builder()
                    .noticeBoardIdx(noticeBoardIdx)
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
            NoticeBoardAttachedFile attachedFile = findAttachedFileByIdx(idx);

            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

            deleteAttachedFileByIdx(attachedFile.getIdx());
        }
    }

    /**
     * 모든 첨부 파일 삭제
     *
     * @param noticeBoardIdx
     */
    public void deleteAllAttachedFile(Long noticeBoardIdx) throws Exception {
        List<NoticeBoardAttachedFile> attachedFileList = noticeBoardAttachedFileRepositoryImpl.findAttachedFileByNoticeBoardIdx(noticeBoardIdx);

        for (NoticeBoardAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByNoticeBoardIdx(noticeBoardIdx);
    }
}