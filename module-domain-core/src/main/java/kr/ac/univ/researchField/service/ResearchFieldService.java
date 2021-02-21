package kr.ac.univ.researchField.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.researchField.domain.ResearchField;
import kr.ac.univ.researchField.domain.enums.ResearchFieldStatus;
import kr.ac.univ.researchField.dto.ResearchFieldDto;
import kr.ac.univ.researchField.dto.mapper.ResearchFieldMapper;
import kr.ac.univ.researchField.repository.ResearchFieldRepository;
import kr.ac.univ.researchField.repository.ResearchFieldRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ResearchFieldService {
    private final ResearchFieldRepository researchFieldRepository;
    private final ResearchFieldRepositoryImpl researchFieldRepositoryImpl;
    private final UserRepository userRepository;

    public ResearchFieldService(ResearchFieldRepository researchFieldRepository, ResearchFieldRepositoryImpl researchFieldRepositoryImpl, UserRepository userRepository) {
        this.researchFieldRepository = researchFieldRepository;
        this.researchFieldRepositoryImpl = researchFieldRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<ResearchFieldDto> findResearchFieldList(Pageable pageable, SearchDto searchDto) {
        Page<ResearchField> researchFieldList = null;
        Page<ResearchFieldDto> researchFieldDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                researchFieldList = researchFieldRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                researchFieldList = researchFieldRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            default:
                researchFieldList = researchFieldRepository.findAll(pageable);
                break;
        }

        researchFieldDtoList = new PageImpl<>(ResearchFieldMapper.INSTANCE.toDto(researchFieldList.getContent()), pageable, researchFieldList.getTotalElements());

        // NewIcon 판별
        for (ResearchFieldDto researchFieldDto : researchFieldDtoList) {
            researchFieldDto.setNewIcon(NewIconCheck.isNew(researchFieldDto.getCreatedDate()));
        }

        return researchFieldDtoList;
    }

    public List<ResearchFieldDto> findResearchFieldListByActiveStatusIs() {
        return ResearchFieldMapper.INSTANCE.toDto(researchFieldRepository.findAllByActiveStatusIs(ActiveStatus.ACTIVE));
    }

    public List<ResearchFieldDto> findAllByActiveStatusIsAndResearchFieldStatusIs() {
        return ResearchFieldMapper.INSTANCE.toDto(researchFieldRepository.findAllByActiveStatusIsAndResearchFieldStatusIs(ActiveStatus.ACTIVE, ResearchFieldStatus.CURRENT));
    }

    public Long insertResearchField(ResearchFieldDto researchFieldDto) {
        return researchFieldRepository.save(ResearchFieldMapper.INSTANCE.toEntity(researchFieldDto)).getIdx();
    }

    public ResearchFieldDto findResearchFieldByIdx(Long idx) {
        ResearchFieldDto researchFieldDto = ResearchFieldMapper.INSTANCE.toDto(researchFieldRepository.findById(idx).orElse(new ResearchField()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            researchFieldDto.setAccess(true);
        }
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        else {
            researchFieldDto.setAccess(AccessCheck.isAccessInGeneral(researchFieldDto.getCreatedBy(), userRepository.findByUsername(researchFieldDto.getCreatedBy()).getAuthorityType().name()));
        }

        researchFieldRepositoryImpl.updateViewsByIdx(idx);
        researchFieldDto.setViews(researchFieldDto.getViews() + 1);

        return researchFieldDto;
    }

    public ResearchFieldDto findResearchFieldByIdxInActive(Long idx) {
        return ResearchFieldMapper.INSTANCE.toDto(researchFieldRepository.findByIdxAndActiveStatusIs(idx, ActiveStatus.ACTIVE));
    }

    @Transactional
    public Long updateResearchField(Long idx, ResearchFieldDto researchFieldDto) {
        ResearchField persistResearchField = researchFieldRepository.getOne(idx);
        ResearchField researchField = ResearchFieldMapper.INSTANCE.toEntity(researchFieldDto);

        persistResearchField.update(researchField);

        return researchFieldRepository.save(persistResearchField).getIdx();
    }

    public void deleteResearchFieldByIdx(Long idx) {
        researchFieldRepository.deleteById(idx);
    }
}