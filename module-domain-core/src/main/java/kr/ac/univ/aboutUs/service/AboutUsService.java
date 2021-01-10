package kr.ac.univ.aboutUs.service;

import kr.ac.univ.aboutUs.domain.AboutUs;
import kr.ac.univ.aboutUs.dto.AboutUsDto;
import kr.ac.univ.aboutUs.dto.mapper.AboutUsMapper;
import kr.ac.univ.aboutUs.repository.AboutUsRepository;
import kr.ac.univ.aboutUs.repository.AboutUsRepositoryImpl;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AboutUsService {
    @Value("${module.name}")
    private String moduleName;
    private final AboutUsRepository aboutUsRepository;
    private final AboutUsRepositoryImpl aboutUsRepositoryImpl;

    public AboutUsService(AboutUsRepository aboutUsRepository, AboutUsRepositoryImpl aboutUsRepositoryImpl) {
        this.aboutUsRepository = aboutUsRepository;
        this.aboutUsRepositoryImpl = aboutUsRepositoryImpl;
    }

    public Page<AboutUsDto> findAboutUsList(Pageable pageable, SearchDto searchDto) {
        Page<AboutUs> aboutUsList = null;
        Page<AboutUsDto> aboutUsDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                aboutUsList = aboutUsRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                break;
            case "CONTENT":
                aboutUsList = aboutUsRepository.findAllByContentContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                aboutUsList = aboutUsRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            default:
                aboutUsList = aboutUsRepository.findAll(pageable);
                break;
        }

        aboutUsDtoList = new PageImpl<AboutUsDto>(AboutUsMapper.INSTANCE.toDto(aboutUsList.getContent()), pageable, aboutUsList.getTotalElements());

        // NewIcon 판별
        for (AboutUsDto aboutUsDto : aboutUsDtoList) {
            aboutUsDto.setNewIcon(NewIconCheck.isNew(aboutUsDto.getCreatedDate()));
        }

        return aboutUsDtoList;
    }

    public AboutUsDto findAboutUsByActiveStatusIs() {
        return AboutUsMapper.INSTANCE.toDto(aboutUsRepository.findAllByActiveStatusIs(ActiveStatus.ACTIVE));
    }

    public Long countAllByActiveStatus() {
        return aboutUsRepository.countAllByActiveStatus(ActiveStatus.ACTIVE);
    }

    public Long insertAboutUs(AboutUsDto aboutUsDto) {
        return aboutUsRepository.save(AboutUsMapper.INSTANCE.toEntity(aboutUsDto)).getIdx();
    }

    public AboutUsDto findAboutUsByIdx(Long idx) {
        AboutUsDto aboutUsDto = AboutUsMapper.INSTANCE.toDto(aboutUsRepository.findById(idx).orElse(new AboutUs()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            aboutUsDto.setAccess(true);
        }
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        else if (AccessCheck.isAccessInModuleWeb(aboutUsDto.getCreatedBy())) {
            aboutUsDto.setAccess(true);
        } else {
            aboutUsDto.setAccess(false);
        }

        aboutUsRepositoryImpl.updateViewsByIdx(idx);
        aboutUsDto.setViews(aboutUsDto.getViews() + 1);

        return aboutUsDto;
    }

    @Transactional
    public Long updateAboutUs(Long idx, AboutUsDto aboutUsDto) {
        AboutUs persistAboutUs = aboutUsRepository.getOne(idx);
        AboutUs aboutUs = AboutUsMapper.INSTANCE.toEntity(aboutUsDto);

        persistAboutUs.update(aboutUs);

        return aboutUsRepository.save(aboutUs).getIdx();
    }

    public void deleteAboutUsByIdx(Long idx) {
        aboutUsRepository.deleteById(idx);
    }
}