package kr.ac.univ.alumniAssociation.service;

import kr.ac.univ.alumniAssociation.domain.AlumniAssociation;
import kr.ac.univ.alumniAssociation.dto.AlumniAssociationDto;
import kr.ac.univ.alumniAssociation.dto.mapper.AlumniAssociationMapper;
import kr.ac.univ.alumniAssociation.repository.AlumniAssociationRepository;
import kr.ac.univ.alumniAssociation.repository.AlumniAssociationRepositoryImpl;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.EmptyUtil;
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
    private final AlumniAssociationRepositoryImpl alumniAssociationRepositoryImpl;
    private final UserRepository userRepository;

    public AlumniAssociationService(AlumniAssociationRepository alumniAssociationRepository, AlumniAssociationRepositoryImpl alumniAssociationRepositoryImpl, UserRepository userRepository) {
        this.alumniAssociationRepository = alumniAssociationRepository;
        this.alumniAssociationRepositoryImpl = alumniAssociationRepositoryImpl;
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
                    alumniAssociationList = alumniAssociationRepository.findAllByOrderByMainPagePriorityAsc(pageable);
                } else if ("module-app-web".equals(moduleName)) {
                    alumniAssociationList = alumniAssociationRepository.findAllByActiveStatusIsOrderByMainPagePriorityAsc(pageable, ActiveStatus.ACTIVE);
                } else {
                    alumniAssociationList = null;
                }
                break;
        }

        alumniAssociationDtoList = new PageImpl<>(AlumniAssociationMapper.INSTANCE.toDto(alumniAssociationList.getContent()), pageable, alumniAssociationList.getTotalElements());

        // NewIcon 판별, Main HashTag 설정
        for (AlumniAssociationDto alumniAssociationDto : alumniAssociationDtoList) {
            alumniAssociationDto.setNewIcon(NewIconCheck.isNew(alumniAssociationDto.getCreatedDate()));
        }

        return alumniAssociationDtoList;
    }

    public List<AlumniAssociationDto> findAllByActiveStatusIsAndMainPagePriorityLessThanEqualOrderByMainPagePriorityAsc() {
        return AlumniAssociationMapper.INSTANCE.toDto(alumniAssociationRepository.findAllByActiveStatusIsAndMainPagePriorityLessThanEqualOrderByMainPagePriorityAsc(ActiveStatus.ACTIVE, 10L));
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
        // 탈퇴 회원은 권한을 general로 설정 후 권한을 검사함
        else {
            User user = userRepository.findByUsername(alumniAssociationDto.getCreatedBy());

            alumniAssociationDto.setAccess(AccessCheck.isAccessInGeneral(alumniAssociationDto.getCreatedBy(), EmptyUtil.isEmpty(user) ? "general" : user.getAuthorityType().getAuthorityType()));
        }

        alumniAssociationRepositoryImpl.updateViewsByIdx(idx);
        alumniAssociationDto.setViews(alumniAssociationDto.getViews() + 1);

        return alumniAssociationDto;
    }

    @Transactional
    public Long updateAlumniAssociation(Long idx, AlumniAssociationDto alumniAssociationDto) {
        AlumniAssociation persistAlumniAssociation = alumniAssociationRepository.getOne(idx);
        AlumniAssociation alumniAssociation = AlumniAssociationMapper.INSTANCE.toEntity(alumniAssociationDto);

        persistAlumniAssociation.update(alumniAssociation);

        return alumniAssociationRepository.save(persistAlumniAssociation).getIdx();
    }

    public void deleteAlumniAssociationByIdx(Long idx) {
        alumniAssociationRepository.deleteById(idx);
    }
}