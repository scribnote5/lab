package kr.ac.univ.album.service;

import kr.ac.univ.album.domain.AlbumAttachedFile;
import kr.ac.univ.album.dto.AlbumDto;
import kr.ac.univ.album.dto.mapper.AlbumMapper;
import kr.ac.univ.album.repository.AlbumAttachedFileRepository;
import kr.ac.univ.album.repository.AlbumAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class AlbumAttachedFileService {
    private final AlbumAttachedFileRepository albumAttachedFileRepository;
    private final AlbumAttachedFileRepositoryImpl albumAttachedFileRepositoryImpl;

    public AlbumAttachedFileService(AlbumAttachedFileRepository albumAttachedFileRepository, AlbumAttachedFileRepositoryImpl albumAttachedFileRepositoryImpl) {
        this.albumAttachedFileRepository = albumAttachedFileRepository;
        this.albumAttachedFileRepositoryImpl = albumAttachedFileRepositoryImpl;
    }

    public AlbumDto findAttachedFileByAlbumIdx(Long albumIdx, AlbumDto albumDto) {

        return AlbumMapper.INSTANCE.toDto(albumDto, albumAttachedFileRepositoryImpl.findAttachedFileByAlbumIdx(albumIdx));
    }

    public void insertAttachedFile(AlbumAttachedFile attachedFile) {
        albumAttachedFileRepository.save(attachedFile);
    }

    public AlbumAttachedFile findAttachedFileByIdx(Long idx) {
        return albumAttachedFileRepository.findById(idx).orElse(new AlbumAttachedFile());
    }

    public AlbumAttachedFile getAttachedFileByIdx(Long idx) {
        return albumAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        albumAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByAlbumIdx(Long idx) {
        return albumAttachedFileRepositoryImpl.deleteAttachedFileByAlbumIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param albumIdx
     * @param files
     */
    public void uploadAttachedFile(Long albumIdx, String createdBy, MultipartFile[] files) throws Exception {
        AlbumAttachedFile uploadFile = new AlbumAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);

            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = AlbumAttachedFile.builder()
                    .albumIdx(albumIdx)
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
            AlbumAttachedFile attachedFile = findAttachedFileByIdx(idx);

            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

            deleteAttachedFileByIdx(attachedFile.getIdx());
        }
    }

    /**
     * 모든 첨부 파일 삭제
     *
     * @param albumIdx
     */
    public void deleteAllAttachedFile(Long albumIdx) throws Exception {
        List<AlbumAttachedFile> attachedFileList = albumAttachedFileRepositoryImpl.findAttachedFileByAlbumIdx(albumIdx);

        for (AlbumAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByAlbumIdx(albumIdx);
    }
}