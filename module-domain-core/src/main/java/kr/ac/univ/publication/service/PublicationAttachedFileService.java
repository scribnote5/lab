package kr.ac.univ.publication.service;

import kr.ac.univ.publication.domain.PublicationAttachedFile;
import kr.ac.univ.publication.dto.PublicationDto;
import kr.ac.univ.publication.dto.mapper.PublicationMapper;
import kr.ac.univ.publication.repository.PublicationAttachedFileRepository;
import kr.ac.univ.publication.repository.PublicationAttachedFileRepositoryImpl;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class PublicationAttachedFileService {
    private final PublicationAttachedFileRepository publicationAttachedFileRepository;
    private final PublicationAttachedFileRepositoryImpl publicationAttachedFileRepositoryImpl;

    public PublicationAttachedFileService(PublicationAttachedFileRepository publicationAttachedFileRepository, PublicationAttachedFileRepositoryImpl publicationAttachedFileRepositoryImpl) {
        this.publicationAttachedFileRepository = publicationAttachedFileRepository;
        this.publicationAttachedFileRepositoryImpl = publicationAttachedFileRepositoryImpl;
    }

    public PublicationDto findAttachedFileByPublicationIdx(Long publicationIdx, PublicationDto publicationDto) {

        return PublicationMapper.INSTANCE.toDto(publicationDto, publicationAttachedFileRepositoryImpl.findAttachedFileByPublicationIdx(publicationIdx));
    }

    public void insertAttachedFile(PublicationAttachedFile attachedFile) {
        publicationAttachedFileRepository.save(attachedFile);
    }

    public PublicationAttachedFile findAttachedFileByIdx(Long idx) {
        return publicationAttachedFileRepository.findById(idx).orElse(new PublicationAttachedFile());
    }

    public PublicationAttachedFile getAttachedFileByIdx(Long idx) {
        return publicationAttachedFileRepository.getOne(idx);
    }

    public void deleteAttachedFileByIdx(Long idx) {
        publicationAttachedFileRepository.deleteById(idx);
    }

    public Long deleteAttachedFileByPublicationIdx(Long idx) {
        return publicationAttachedFileRepositoryImpl.deleteAttachedFileByPublicationIdx(idx);
    }

    /**
     * 첨부 파일 업로드
     *
     * @param publicationIdx
     * @param files
     */
    public void uploadAttachedFile(Long publicationIdx, String createdBy, MultipartFile[] files) throws Exception {
        PublicationAttachedFile uploadFile = new PublicationAttachedFile();

        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String savedFileName = uuid + "_" + file.getOriginalFilename();

            // 대체 가능하다
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);

            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());

            uploadFile = PublicationAttachedFile.builder()
                    .publicationIdx(publicationIdx)
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
            PublicationAttachedFile attachedFile = findAttachedFileByIdx(idx);

            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);

            deleteAttachedFileByIdx(attachedFile.getIdx());
        }
    }

    /**
     * 모든 첨부 파일 삭제
     *
     * @param publicationIdx
     */
    public void deleteAllAttachedFile(Long publicationIdx) throws Exception {
        List<PublicationAttachedFile> attachedFileList = publicationAttachedFileRepositoryImpl.findAttachedFileByPublicationIdx(publicationIdx);

        for (PublicationAttachedFile attachedFile : attachedFileList) {
            Path path = Paths.get("./upload/" + attachedFile.getSavedFileName());
            Files.delete(path);
        }

        deleteAttachedFileByPublicationIdx(publicationIdx);
    }
}