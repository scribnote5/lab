package kr.ac.univ.album.service;

import kr.ac.univ.album.domain.Album;
import kr.ac.univ.album.dto.AlbumDto;
import kr.ac.univ.album.dto.mapper.AlbumMapper;
import kr.ac.univ.album.repository.AlbumRepository;
import kr.ac.univ.album.repository.AlbumRepositoryImpl;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AlbumService {
    @Value("${module.name}")
    private String moduleName;
    private final AlbumRepository albumRepository;
    private final AlbumRepositoryImpl albumRepositoryImpl;

    public AlbumService(AlbumRepository albumRepository, AlbumRepositoryImpl albumRepositoryImpl) {
        this.albumRepository = albumRepository;
        this.albumRepositoryImpl = albumRepositoryImpl;
    }

    public Page<AlbumDto> findAlbumList(Pageable pageable, SearchDto searchDto) {
        Page<Album> albumList = null;
        Page<AlbumDto> albumDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                if ("module-app-admin".equals(moduleName)) {
                    albumList = albumRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    albumList = albumRepository.findAllByTitleContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    albumList = null;
                }
                break;
            case "HASH_TAGS":
                if ("module-app-admin".equals(moduleName)) {
                    albumList = albumRepository.findAllByHashTagsContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    albumList = albumRepository.findAllByHashTagsContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    albumList = null;
                }
                break;
            case "ID":
                if ("module-app-admin".equals(moduleName)) {
                    albumList = albumRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    albumList = albumRepository.findAllByCreatedByContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    albumList = null;
                }
                break;
            default:
                if ("module-app-admin".equals(moduleName)) {
                    albumList = albumRepository.findAll(pageable);
                } else if ("module-app-web".equals(moduleName)) {
                    albumList = albumRepository.findAllByActiveStatusIs(pageable, ActiveStatus.ACTIVE);
                } else {
                    albumList = null;
                }
                break;
        }

        albumDtoList = new PageImpl<AlbumDto>(AlbumMapper.INSTANCE.toDto(albumList.getContent()), pageable, albumList.getTotalElements());

        // NewIcon 판별
        for (AlbumDto albumDto : albumDtoList) {
            albumDto.setNewIcon(NewIconCheck.isNew(albumDto.getCreatedDate()));
        }

        return albumDtoList;
    }

    public List<AlbumDto> findAllByActiveStatusIsOrderByMainHashTagDescIdxDesc() {
        List<AlbumDto> albumDtoList = AlbumMapper.INSTANCE.toDto(albumRepository.findAllByActiveStatusIsOrderByMainHashTagDescIdxDesc(ActiveStatus.ACTIVE));
        String mainHashTag = "";

        for (AlbumDto albumDto : albumDtoList) {
            if (!mainHashTag.equals(albumDto.getMainHashTag())) {
                albumDto.setMainHashTagPrint(true);
                mainHashTag = albumDto.getMainHashTag();
            } else {
                albumDto.setMainHashTagPrint(false);
            }
        }

        return albumDtoList;
    }

    public List<AlbumDto> findAllByActiveStatusIsAndMainPagePriorityGreaterThanEqualOrderByMainPagePriorityAsc() {
        return AlbumMapper.INSTANCE.toDto(albumRepository.findAllByActiveStatusIsAndMainPagePriorityGreaterThanEqualOrderByMainPagePriorityAsc(ActiveStatus.ACTIVE, 0L));
    }

    public Long insertAlbum(AlbumDto albumDto) {
        // mainHashTag 설정
        albumDto.setMainHashTag("#" + (albumDto.getHashTags()).split("#")[1]);

        return albumRepository.save(AlbumMapper.INSTANCE.toEntity(albumDto)).getIdx();
    }

    public AlbumDto findAlbumByIdx(Long idx) {
        AlbumDto albumDto = AlbumMapper.INSTANCE.toDto(albumRepository.findById(idx).orElse(new Album()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            albumDto.setAccess(true);
        }
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        else if (AccessCheck.isAccessInModuleWeb(albumDto.getCreatedBy())) {
            albumDto.setAccess(true);
        } else {
            albumDto.setAccess(false);
        }

        albumRepositoryImpl.updateViewsByIdx(idx);
        albumDto.setViews(albumDto.getViews() + 1);

        return albumDto;
    }

    public AlbumDto findByMainPagePriorityIs(Long idx, Long mainPagePriority) {
        AlbumDto albumDto = null;

        // insert하는 경우와 자신의 album mainPagePriority와 저장하는 mainPagePrioirty가 중복 되는 경우는 패스
        if (idx != null && albumRepository.findById(idx).orElse(new Album()).getMainPagePriority() != mainPagePriority) {
            albumDto = AlbumMapper.INSTANCE.toDto(albumRepository.findByMainPagePriorityIsAndActiveStatusIs(mainPagePriority, ActiveStatus.ACTIVE));
        }

        return albumDto;
    }

    @Transactional
    public Long updateAlbum(Long idx, AlbumDto albumDto) {
        // mainHashTag 설정
        albumDto.setMainHashTag("#" + (albumDto.getHashTags()).split("#")[1]);

        Album persistAlbum = albumRepository.getOne(idx);
        Album album = AlbumMapper.INSTANCE.toEntity(albumDto);

        persistAlbum.update(album);

        return albumRepository.save(persistAlbum).getIdx();
    }

    public void deleteAlbumByIdx(Long idx) {
        albumRepository.deleteById(idx);
    }
}