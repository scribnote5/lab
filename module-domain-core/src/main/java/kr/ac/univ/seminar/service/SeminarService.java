package kr.ac.univ.seminar.service;

import kr.ac.univ.category.repository.CategoryRepository;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.noticeBoard.dto.NoticeBoardDto;
import kr.ac.univ.noticeBoard.dto.mapper.NoticeBoardMapper;
import kr.ac.univ.seminar.domain.Seminar;
import kr.ac.univ.seminar.dto.SeminarDto;
import kr.ac.univ.seminar.dto.mapper.SeminarMapper;
import kr.ac.univ.seminar.repository.SeminarRepository;
import kr.ac.univ.seminar.repository.SeminarRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
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
    private final CategoryRepository categoryRepository;

    public SeminarService(SeminarRepository seminarRepository, SeminarRepositoryImpl seminarRepositoryImpl, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.seminarRepository = seminarRepository;
        this.seminarRepositoryImpl = seminarRepositoryImpl;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Page<SeminarDto> findSeminarList(Pageable pageable, SearchDto searchDto) {
        Page<Seminar> seminarList = null;
        Page<SeminarDto> seminarDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                if ("module-app-admin".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByTitleContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    seminarList = null;
                }
                break;
            case "PRESENTER":
                if ("module-app-admin".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByPresenterContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByPresenterContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    seminarList = null;
                }
                break;
            default:
                if ("module-app-admin".equals(moduleName)) {
                    seminarList = seminarRepository.findAll(pageable);
                } else if ("module-app-web".equals(moduleName)) {
                    seminarList = seminarRepository.findAllByActiveStatusIs(pageable, ActiveStatus.ACTIVE);
                } else {
                    seminarList = null;
                }
                break;
        }

        seminarDtoList = new PageImpl<SeminarDto>(SeminarMapper.INSTANCE.toDto(seminarList.getContent()), pageable, seminarList.getTotalElements());

        // NewIcon 판별
        for (SeminarDto seminarDto : seminarDtoList) {
            seminarDto.setNewIcon(NewIconCheck.isNew(seminarDto.getCreatedDate()));
        }

        return seminarDtoList;
    }

    public List<SeminarDto> findSeminarList() {
        List<SeminarDto> seminarDtoList = SeminarMapper.INSTANCE.toDto(seminarRepository.findTop10ByOrderByIdxDesc());

        // NewIcon 판별
        for (SeminarDto seminarDto : seminarDtoList) {
            seminarDto.setNewIcon(NewIconCheck.isNew(seminarDto.getCreatedDate()));
        }

        return seminarDtoList;
    }

    public Long insertSeminar(SeminarDto seminarDto) {
        return seminarRepository.save(SeminarMapper.INSTANCE.toEntity(seminarDto)).getIdx();
    }

    @Transactional
    public SeminarDto findSeminarByIdx(Long idx) {
        SeminarDto seminarDto = SeminarMapper.INSTANCE.toDto(seminarRepository.findById(idx).orElse(new Seminar()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            seminarDto.setAccess(true);
        }
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        else if (AccessCheck.isAccessInModuleWeb(seminarDto.getCreatedBy())) {
            seminarDto.setAccess(true);
        } else {
            seminarDto.setAccess(false);
        }

        seminarRepositoryImpl.updateViewsByIdx(idx);

        return seminarDto;
    }

    @Transactional
    public Long updateSeminar(Long idx, SeminarDto seminarDto) {
        Seminar persistSeminar = seminarRepository.getOne(idx);
        Seminar seminar = SeminarMapper.INSTANCE.toEntity(seminarDto);

        persistSeminar.update(seminar);

        return seminarRepository.save(seminar).getIdx();
    }

    public void deleteSeminarByIdx(Long idx) {
        seminarRepository.deleteById(idx);
    }
}