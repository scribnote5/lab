package kr.ac.univ.introductionImage.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.introductionImage.domain.IntroductionImage;
import kr.ac.univ.introductionImage.dto.IntroductionImageDto;
import kr.ac.univ.introductionImage.dto.mapper.IntroductionImageMapper;
import kr.ac.univ.introductionImage.repository.IntroductionImageRepository;
import kr.ac.univ.introductionImage.repository.IntroductionImageRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class IntroductionImageService {
    @Value("${module.name}")
    private String moduleName;
    private final IntroductionImageRepository introductionImageRepository;
    private final IntroductionImageRepositoryImpl introductionImageRepositoryImpl;
    private final UserRepository userRepository;

    public IntroductionImageService(IntroductionImageRepository introductionImageRepository, IntroductionImageRepositoryImpl introductionImageRepositoryImpl, UserRepository userRepository) {
        this.introductionImageRepository = introductionImageRepository;
        this.introductionImageRepositoryImpl = introductionImageRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<IntroductionImageDto> findIntroductionImageList(Pageable pageable, SearchDto searchDto) {
        Page<IntroductionImage> introductionImageList = null;
        Page<IntroductionImageDto> introductionImageDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                if ("module-app-admin".equals(moduleName)) {
                    introductionImageList = introductionImageRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    introductionImageList = introductionImageRepository.findAllByTitleContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    introductionImageList = null;
                }
                break;
            case "ID":
                if ("module-app-admin".equals(moduleName)) {
                    introductionImageList = introductionImageRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    introductionImageList = introductionImageRepository.findAllByCreatedByContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    introductionImageList = null;
                }
                break;
            default:
                if ("module-app-admin".equals(moduleName)) {
                    introductionImageList = introductionImageRepository.findAll(pageable);
                } else if ("module-app-web".equals(moduleName)) {
                    introductionImageList = introductionImageRepository.findAllByActiveStatusIs(pageable, ActiveStatus.ACTIVE);
                } else {
                    introductionImageList = null;
                }
                break;
        }

        introductionImageDtoList = new PageImpl<>(IntroductionImageMapper.INSTANCE.toDto(introductionImageList.getContent()), pageable, introductionImageList.getTotalElements());

        // NewIcon 판별
        for (IntroductionImageDto introductionImageDto : introductionImageDtoList) {
            introductionImageDto.setNewIcon(NewIconCheck.isNew(introductionImageDto.getCreatedDate()));
        }

        return introductionImageDtoList;
    }

    public List<IntroductionImageDto> findIntroductionImageListByActiveStatusIs() {
        return IntroductionImageMapper.INSTANCE.toDto(introductionImageRepository.findAllByActiveStatusIsAndMainPagePriorityGreaterThanEqualOrderByMainPagePriorityAsc(ActiveStatus.ACTIVE, 0L));
    }

    public Long insertIntroductionImage(IntroductionImageDto introductionImageDto) {
        return introductionImageRepository.save(IntroductionImageMapper.INSTANCE.toEntity(introductionImageDto)).getIdx();
    }

    public IntroductionImageDto findIntroductionImageByIdx(Long idx) {
        IntroductionImageDto introductionImageDto = IntroductionImageMapper.INSTANCE.toDto(introductionImageRepository.findById(idx).orElse(new IntroductionImage()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            introductionImageDto.setAccess(true);
        }
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        else {
            introductionImageDto.setAccess(AccessCheck.isAccessInGeneral(introductionImageDto.getCreatedBy(), userRepository.findByUsername(introductionImageDto.getCreatedBy()).getAuthorityType().name()));
        }

        introductionImageRepositoryImpl.updateViewsByIdx(idx);
        introductionImageDto.setViews(introductionImageDto.getViews() + 1);

        return introductionImageDto;
    }

    public IntroductionImageDto findByMainPagePriorityIs(Long idx, Long mainPagePriority) {
        IntroductionImageDto introductionImageDto = null;

        // insert하는 경우와 자신의 introductionImage mainPagePriority와 저장하는 mainPagePrioirty가 중복 되는 경우는 패스
        if (idx != null && introductionImageRepository.findById(idx).orElse(new IntroductionImage()).getMainPagePriority() != mainPagePriority) {
            introductionImageDto = IntroductionImageMapper.INSTANCE.toDto(introductionImageRepository.findByMainPagePriorityIsAndActiveStatusIs(mainPagePriority, ActiveStatus.ACTIVE));
        }

        return introductionImageDto;
    }

    @Transactional
    public Long updateIntroductionImage(Long idx, IntroductionImageDto introductionImageDto) {
        IntroductionImage persistIntroductionImage = introductionImageRepository.getOne(idx);
        IntroductionImage introductionImage = IntroductionImageMapper.INSTANCE.toEntity(introductionImageDto);

        persistIntroductionImage.update(introductionImage);

        return introductionImageRepository.save(persistIntroductionImage).getIdx();
    }

    public void deleteIntroductionImageByIdx(Long idx) {
        introductionImageRepository.deleteById(idx);
    }
}