package kr.ac.univ.alumniAssociation.service;

import kr.ac.univ.album.domain.Album;
import kr.ac.univ.album.dto.AlbumDto;
import kr.ac.univ.album.dto.mapper.AlbumMapper;
import kr.ac.univ.alumniAssociation.domain.AlumniAssociation;
import kr.ac.univ.alumniAssociation.dto.AlumniAssociationDto;
import kr.ac.univ.alumniAssociation.dto.mapper.AlumniAssociationMapper;
import kr.ac.univ.alumniAssociation.repository.AlumniAssociationRepository;
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
public class AlumniAssociationService {
    @Value("${module.name}")
    private String moduleName;
    private final AlumniAssociationRepository alumniAssociationRepository;
    private final UserRepository userRepository;

    public AlumniAssociationService(AlumniAssociationRepository alumniAssociationRepository, UserRepository userRepository) {
        this.alumniAssociationRepository = alumniAssociationRepository;
        this.userRepository = userRepository;
    }

    public Page<AlumniAssociationDto> findAlumniAssociationList(Pageable pageable, SearchDto searchDto) {
        Page<AlumniAssociation> alumniAssociationList = null;
        Page<AlumniAssociationDto> alumniAssociationDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                if ("module-app-admin".equals(moduleName)) {
                    alumniAssociationList = alumniAssociationRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    alumniAssociationList = alumniAssociationRepository.findAllByTitleContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    alumniAssociationList = null;
                }
                break;
            case "Content":
                if ("module-app-admin".equals(moduleName)) {
                    alumniAssociationList = alumniAssociationRepository.findAllByContentContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    alumniAssociationList = alumniAssociationRepository.findAllByContentContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    alumniAssociationList = null;
                }
                break;
            case "ID":
                if ("module-app-admin".equals(moduleName)) {
                    alumniAssociationList = alumniAssociationRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    alumniAssociationList = alumniAssociationRepository.findAllByCreatedByContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    alumniAssociationList = null;
                }
                break;
            default:
                if ("module-app-admin".equals(moduleName)) {
                    alumniAssociationList = alumniAssociationRepository.findAll(pageable);
                } else if ("module-app-web".equals(moduleName)) {
                    alumniAssociationList = alumniAssociationRepository.findAllByActiveStatusIs(pageable, ActiveStatus.ACTIVE);
                } else {
                    alumniAssociationList = null;
                }
                break;
        }

        alumniAssociationDtoList = new PageImpl<AlumniAssociationDto>(AlumniAssociationMapper.INSTANCE.toDto(alumniAssociationList.getContent()), pageable, alumniAssociationList.getTotalElements());

        // NewIcon 판별, Main HashTag 설정
        for (AlumniAssociationDto alumniAssociationDto : alumniAssociationDtoList) {
            alumniAssociationDto.setNewIcon(NewIconCheck.isNew(alumniAssociationDto.getCreatedDate()));
        }

        return alumniAssociationDtoList;
    }

    public List<AlumniAssociationDto> findAlumniAssociationListByActiveStatusIs() {
        return AlumniAssociationMapper.INSTANCE.toDto(alumniAssociationRepository.findAllByActiveStatusIsAndMainPagePriorityGreaterThanEqualOrderByMainPagePriorityAsc(ActiveStatus.ACTIVE, 0L));
    }

    public Long insertAlumniAssociation(AlumniAssociationDto alumniAssociationDto) {
        return alumniAssociationRepository.save(AlumniAssociationMapper.INSTANCE.toEntity(alumniAssociationDto)).getIdx();
    }

    public AlumniAssociationDto findAlumniAssociationByIdx(Long idx) {
        AlumniAssociationDto alumniAssociationDto = AlumniAssociationMapper.INSTANCE.toDto(alumniAssociationRepository.findById(idx).orElse(new AlumniAssociation()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            alumniAssociationDto.setAccess(true);
        }
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        else if (AccessCheck.isAccessInModuleWeb(alumniAssociationDto.getCreatedBy())) {
            alumniAssociationDto.setAccess(true);
        } else {
            alumniAssociationDto.setAccess(false);
        }

        return alumniAssociationDto;
    }

    public AlumniAssociationDto findByMainPagePriorityIs(Long idx, Long mainPagePriority) {
        AlumniAssociationDto alumniAssociationDto = null;

        // insert하는 경우와 자신의 album mainPagePriority와 저장하는 mainPagePrioirty가 중복 되는 경우는 패스
        if (idx != null && alumniAssociationRepository.findById(idx).orElse(new AlumniAssociation()).getMainPagePriority() != mainPagePriority) {
            alumniAssociationDto = AlumniAssociationMapper.INSTANCE.toDto(alumniAssociationRepository.findByMainPagePriorityIsAndActiveStatusIs(mainPagePriority, ActiveStatus.ACTIVE));
        }

        return alumniAssociationDto;
    }

    @Transactional
    public Long updateAlumniAssociation(Long idx, AlumniAssociationDto alumniAssociationDto) {
        AlumniAssociation persistAlumniAssociation = alumniAssociationRepository.getOne(idx);
        AlumniAssociation alumniAssociation = AlumniAssociationMapper.INSTANCE.toEntity(alumniAssociationDto);

        persistAlumniAssociation.update(alumniAssociation);

        return alumniAssociationRepository.save(alumniAssociation).getIdx();
    }

    public void deleteAlumniAssociationByIdx(Long idx) {
        alumniAssociationRepository.deleteById(idx);
    }
}