package kr.ac.univ.introduction.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.introduction.domain.Introduction;
import kr.ac.univ.introduction.dto.IntroductionDto;
import kr.ac.univ.introduction.dto.mapper.IntroductionMapper;
import kr.ac.univ.introduction.repository.IntroductionRepository;
import kr.ac.univ.introduction.repository.IntroductionRepositoryImpl;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class IntroductionService {
    private final IntroductionRepository introductionRepository;
    private final IntroductionRepositoryImpl introductionRepositoryImpl;

    public IntroductionService(IntroductionRepository introductionRepository, IntroductionRepositoryImpl introductionRepositoryImpl) {
        this.introductionRepository = introductionRepository;
        this.introductionRepositoryImpl = introductionRepositoryImpl;
    }

    public Page<IntroductionDto> findIntroductionList(Pageable pageable, SearchDto searchDto) {
        Page<Introduction> introductionList = null;
        Page<IntroductionDto> introductionDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                introductionList = introductionRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                break;
            case "CONTENT":
                introductionList = introductionRepository.findAllByContentContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                introductionList = introductionRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            default:
                introductionList = introductionRepository.findAll(pageable);
                break;
        }

        introductionDtoList = new PageImpl<IntroductionDto>(IntroductionMapper.INSTANCE.toDto(introductionList.getContent()), pageable, introductionList.getTotalElements());

        // NewIcon 판별
        for (IntroductionDto introductionDto : introductionDtoList) {
            introductionDto.setNewIcon(NewIconCheck.isNew(introductionDto.getCreatedDate()));
        }

        return introductionDtoList;
    }

    public IntroductionDto findIntroductionByActiveStatusIs() {
        return IntroductionMapper.INSTANCE.toDto(introductionRepository.findByActiveStatusIs(ActiveStatus.ACTIVE));
    }

    public Long countAllByActiveStatus() {
        return introductionRepository.countAllByActiveStatus(ActiveStatus.ACTIVE);
    }

    public Long insertIntroduction(IntroductionDto introductionDto) {
        return introductionRepository.save(IntroductionMapper.INSTANCE.toEntity(introductionDto)).getIdx();
    }

    public IntroductionDto findIntroductionByIdx(Long idx) {
        IntroductionDto introductionDto = IntroductionMapper.INSTANCE.toDto(introductionRepository.findById(idx).orElse(new Introduction()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            introductionDto.setAccess(true);
        }
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        else if (AccessCheck.isAccessInModuleWeb(introductionDto.getCreatedBy())) {
            introductionDto.setAccess(true);
        } else {
            introductionDto.setAccess(false);
        }

        introductionRepositoryImpl.updateViewsByIdx(idx);
        introductionDto.setViews(introductionDto.getViews() + 1);

        return introductionDto;
    }

    @Transactional
    public Long updateIntroduction(Long idx, IntroductionDto introductionDto) {
        Introduction persistIntroduction = introductionRepository.getOne(idx);
        Introduction introduction = IntroductionMapper.INSTANCE.toEntity(introductionDto);

        persistIntroduction.update(introduction);

        return introductionRepository.save(introduction).getIdx();
    }

    public void deleteIntroductionByIdx(Long idx) {
        introductionRepository.deleteById(idx);
    }
}