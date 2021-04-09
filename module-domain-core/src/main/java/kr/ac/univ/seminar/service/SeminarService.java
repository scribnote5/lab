package kr.ac.univ.seminar.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.seminar.domain.Seminar;
import kr.ac.univ.seminar.dto.SeminarDto;
import kr.ac.univ.seminar.dto.mapper.SeminarMapper;
import kr.ac.univ.seminar.repository.SeminarRepository;
import kr.ac.univ.seminar.repository.SeminarRepositoryImpl;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.dto.UserDto;
import kr.ac.univ.user.dto.mapper.UserMapper;
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
public class SeminarService {
    @Value("${module.name}")
    private String moduleName;
    private final SeminarRepository seminarRepository;
    private final SeminarRepositoryImpl seminarRepositoryImpl;
    private final UserRepository userRepository;

    public SeminarService(SeminarRepository seminarRepository, SeminarRepositoryImpl seminarRepositoryImpl, UserRepository userRepository) {
        this.seminarRepository = seminarRepository;
        this.seminarRepositoryImpl = seminarRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<SeminarDto> findSeminarList(Pageable pageable, SearchDto searchDto) {
        Page<Seminar> seminarList = null;
        Page<SeminarDto> seminarDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                if ("module-app-admin".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByTitleContainingOrderByPresentationDateDesc(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByTitleContainingAndActiveStatusIsOrderByPresentationDateDesc(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    seminarList = null;
                }
                break;
            case "CONTENT":
                if ("module-app-admin".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByContentContainingOrderByPresentationDateDesc(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByContentContainingAndActiveStatusIsOrderByPresentationDateDesc(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    seminarList = null;
                }
                break;
            case "ID":
                if ("module-app-admin".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByCreatedByContainingOrderByPresentationDateDesc(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByCreatedByContainingAndActiveStatusIsOrderByPresentationDateDesc(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    seminarList = null;
                }
                break;
            case "PRESENTER":
                if ("module-app-admin".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByPresenterContainingOrderByPresentationDateDesc(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByPresenterContainingAndActiveStatusIsOrderByPresentationDateDesc(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    seminarList = null;
                }
                break;
            case "ENGLISH_NAME":
                if ("module-app-admin".equals(moduleName)) {
                    seminarList = seminarRepositoryImpl.findSeminarByUserEnglishName(pageable, searchDto.getKeyword(), moduleName);
                } else if ("module-app-web".equals(moduleName)) {
                    seminarList = seminarRepositoryImpl.findSeminarByUserEnglishName(pageable, searchDto.getKeyword(), moduleName);
                } else {
                    seminarList = null;
                }
                break;
            default:
                if ("module-app-admin".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByOrderByPresentationDateDesc(pageable);
                } else if ("module-app-web".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByActiveStatusIsOrderByPresentationDateDesc(pageable, ActiveStatus.ACTIVE);
                } else {
                    seminarList = null;
                }
                break;
        }

        seminarDtoList = new PageImpl<>(SeminarMapper.INSTANCE.toDto(seminarList.getContent()), pageable, seminarList.getTotalElements());

        // NewIcon 판별
        for (SeminarDto seminarDto : seminarDtoList) {
            seminarDto.setNewIcon(NewIconCheck.isNew(seminarDto.getCreatedDate()));
        }


        return seminarDtoList;
    }

    public List<SeminarDto> findTop6ByOrderByPresentationDateDesc() {
        List<SeminarDto> seminarDtoList = SeminarMapper.INSTANCE.toDto(seminarRepository.findTop6ByOrderByPresentationDateDesc());

        // NewIcon 판별
        for (SeminarDto seminarDto : seminarDtoList) {
            seminarDto.setNewIcon(NewIconCheck.isNew(seminarDto.getCreatedDate()));
        }

        return seminarDtoList;
    }

    public Long insertSeminar(SeminarDto seminarDto) {
        return seminarRepository.save(SeminarMapper.INSTANCE.toEntity(seminarDto)).getIdx();
    }

    public SeminarDto findSeminarByIdx(Long idx) {
        SeminarDto seminarDto = SeminarMapper.INSTANCE.toDto(seminarRepository.findById(idx).orElse(new Seminar()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            seminarDto.setAccess(true);
        }
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        // 탈퇴 회원은 권한을 general로 설정 후 권한을 검사함
        else {
            User user = userRepository.findByUsername(seminarDto.getCreatedBy());

            seminarDto.setAccess(AccessCheck.isAccessInGeneral(seminarDto.getCreatedBy(), EmptyUtil.isEmpty(user) ? "general" : user.getAuthorityType().getAuthorityType()));
        }

        seminarRepositoryImpl.updateViewsByIdx(idx);
        seminarDto.setViews(seminarDto.getViews() + 1);

        return seminarDto;
    }

    @Transactional
    public Long updateSeminar(Long idx, SeminarDto seminarDto) {
        Seminar persistSeminar = seminarRepository.getOne(idx);
        Seminar seminar = SeminarMapper.INSTANCE.toEntity(seminarDto);

        persistSeminar.update(seminar);

        return seminarRepository.save(persistSeminar).getIdx();
    }

    public void deleteSeminarByIdx(Long idx) {
        seminarRepository.deleteById(idx);
    }
}