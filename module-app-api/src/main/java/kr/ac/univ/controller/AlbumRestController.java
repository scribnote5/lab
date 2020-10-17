package kr.ac.univ.controller;

import kr.ac.univ.common.validation.FileValidator;
import kr.ac.univ.error.ErrorCode;
import kr.ac.univ.exception.BusinessException;
import kr.ac.univ.exception.FileNumberExceededException;
import kr.ac.univ.exception.FileTypeException;
import kr.ac.univ.album.dto.AlbumDto;
import kr.ac.univ.album.service.AlbumAttachedFileService;
import kr.ac.univ.album.service.AlbumService;
import kr.ac.univ.util.EmptyUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumRestController {
    private final AlbumService albumService;
    private final AlbumAttachedFileService albumAttachedFileService;

    public AlbumRestController(AlbumService albumService, AlbumAttachedFileService albumAttachedFileService) {
        this.albumService = albumService;
        this.albumAttachedFileService = albumAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postAlbum(@RequestBody @Valid AlbumDto albumDto) {
        if(albumDto.getMainPagePriority() != - 1 && !EmptyUtil.isEmpty(albumService.findByMainPagePriorityIs(albumDto.getIdx(), albumDto.getMainPagePriority())))  {
            throw new BusinessException(ErrorCode.MAIN_PAGE_PRIORITY_DUPLICATE);
        }

        Long idx = albumService.insertAlbum(albumDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putAlbum(@PathVariable("idx") Long idx, @RequestBody @Valid AlbumDto albumDto) {
        if(albumDto.getMainPagePriority() != - 1 && !EmptyUtil.isEmpty(albumService.findByMainPagePriorityIs(albumDto.getIdx(), albumDto.getMainPagePriority())))  {
            throw new BusinessException(ErrorCode.MAIN_PAGE_PRIORITY_DUPLICATE);
        }

        albumService.updateAlbum(idx, albumDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteAlbum(@PathVariable("idx") Long idx) throws Exception {
        albumService.deleteAlbumByIdx(idx);
        albumAttachedFileService.deleteAllAttachedFile(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    // 첨부 파일 업로드
    @PostMapping("/attachedFile")
    public ResponseEntity<?> uploadAttachedFile(Long idx, String createdBy, MultipartFile[] files) throws Exception {
        if (files.length >= 2) {
            throw new FileNumberExceededException("The number of files that can be uploaded is 1.");
        }

        String fileValidationResult = FileValidator.isFileValid(files);

        // 파일 mime type 검사
        if (!"valid".equals(fileValidationResult)) {
            throw new FileTypeException(fileValidationResult);
        }

        albumAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile")
    public ResponseEntity<?> deleteAttachedFile(@RequestBody List<Long> deleteAttachedFileIdxList) throws Exception {
        albumAttachedFileService.deleteAttachedFile(deleteAttachedFileIdxList);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}