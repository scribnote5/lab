package kr.ac.univ.publication.service;


import java.util.List;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.publication.domain.Publication;
import kr.ac.univ.publication.domain.enums.PublicationType;
import kr.ac.univ.publication.domain.enums.PublishingArea;
import kr.ac.univ.publication.dto.PublicationDto;
import kr.ac.univ.publication.dto.PublicationSearchDto;
import kr.ac.univ.publication.dto.mapper.PublicationMapper;
import kr.ac.univ.publication.repository.PublicationRepository;
import kr.ac.univ.publication.repository.PublicationRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class PublicationService {
    @Value("${module.name}")
    private String moduleName;
    private final PublicationRepository publicationRepository;
    private final PublicationRepositoryImpl publicationRepositoryImpl;

    public PublicationService(PublicationRepository publicationRepository, PublicationRepositoryImpl publicationRepositoryImpl) {
        this.publicationRepository = publicationRepository;
        this.publicationRepositoryImpl = publicationRepositoryImpl;
    }

    public Page<PublicationDto> findPublicationList(Pageable pageable, PublicationSearchDto publicationSearchDto) {
        Page<Publication> publicationList = null;
        Page<PublicationDto> publicationDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        if ("Show All".equals(publicationSearchDto.getPublicationSearchType().getSearchPublicationType())) {
            switch (publicationSearchDto.getSearchType()) {
                case "TITLE":
                    publicationList = publicationRepository.findAllByTitleContaining(pageable, publicationSearchDto.getKeyword());
                    break;
                case "AUTHORS":
                    publicationList = publicationRepository.findAllByAuthorsContaining(pageable, publicationSearchDto.getKeyword());
                    break;
                case "PUBLISHED_IN":
                    publicationList = publicationRepository.findAllByPublishedInContaining(pageable, publicationSearchDto.getKeyword());
                    break;
                default:
                    publicationList = publicationRepository.findAll(pageable);
                    break;
            }
        } else {
            String[] str = publicationSearchDto.getPublicationSearchType().getSearchPublicationType().split(" ");
            String parsedString = "";

            PublishingArea publishingArea = PublishingArea.valueOf(str[0].toUpperCase());

            for (int i = 1; i < str.length; i++) {
                parsedString += str[i];
            }

            parsedString = parsedString.toUpperCase().replaceAll("-", "_");
            PublicationType publicationType = PublicationType.valueOf(parsedString.toUpperCase().replaceAll(" ", ""));

            switch (publicationSearchDto.getSearchType()) {
                case "TITLE":
                    publicationList = publicationRepository.findAllByTitleContainingAndPublicationTypeAndPublishingArea(pageable, publicationSearchDto.getKeyword(), publicationType, publishingArea);
                    break;
                case "AUTHORS":
                    publicationList = publicationRepository.findAllByAuthorsContainingAndPublicationTypeAndPublishingArea(pageable, publicationSearchDto.getKeyword(), publicationType, publishingArea);
                    break;
                case "PUBLISHED_IN":
                    publicationList = publicationRepository.findAllByPublishedInContainingAndPublicationTypeAndPublishingArea(pageable, publicationSearchDto.getKeyword(), publicationType, publishingArea);
                    break;
                default:
                    publicationList = publicationRepository.findAllByPublicationTypeAndPublishingArea(pageable, publicationType, publishingArea);
                    break;
            }
        }

        publicationDtoList = new PageImpl<PublicationDto>(PublicationMapper.INSTANCE.toDto(publicationList.getContent()), pageable, publicationList.getTotalElements());

        // NewIcon 판별
        for (PublicationDto publicationDto : publicationDtoList) {
            publicationDto.setNewIcon(NewIconCheck.isNew(publicationDto.getCreatedDate()));
        }

        return publicationDtoList;
    }

    public List<PublicationDto> findPublicationListScroll(Long lastIdx, PublicationSearchDto publicationSearchDto) {
        List<Publication> publicationList = null;
        List<PublicationDto> publicationDtoList = null;

        publicationList = publicationRepositoryImpl.findTop10ByPublicationSearchDto(lastIdx, publicationSearchDto);

        // Publication -> PublicationDto
        publicationDtoList = PublicationMapper.INSTANCE.toDto(publicationList);

        return publicationDtoList;
    }

    public Long countAllByActiveStatusIs() {
        return publicationRepository.countAllByActiveStatusIs(ActiveStatus.ACTIVE);
    }

    public Long insertPublication(Publication publication) {
        return publicationRepository.save(publication).getIdx();
    }

    public PublicationDto findPublicationByIdx(Long idx) {
        PublicationDto publicationDto = PublicationMapper.INSTANCE.toDto(publicationRepository.findById(idx).orElse(new Publication()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            publicationDto.setAccess(true);
        }
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        else if (AccessCheck.isAccessInModuleWeb(publicationDto.getCreatedBy())) {
            publicationDto.setAccess(true);
        } else {
            publicationDto.setAccess(false);
        }

        publicationRepositoryImpl.updateViewsByIdx(idx);
        publicationDto.setViews(publicationDto.getViews() + 1);

        return publicationDto;
    }

    @Transactional
    public Long updatePublication(Long idx, PublicationDto publicationDto) {
        Publication persistPublication = publicationRepository.getOne(idx);
        Publication publication = PublicationMapper.INSTANCE.toEntity(publicationDto);

        persistPublication.update(publication);

        return publicationRepository.save(publication).getIdx();
    }

    public Long findMaxPublicationIdx() {
        Long lastIdx = 0L;

        try {
            lastIdx = publicationRepositoryImpl.findMaxPublicationIdx().getIdx();
        } catch (NullPointerException e) {
            lastIdx = 0L;
        }

        return lastIdx;
    }

    public void deletePublicationByIdx(Long idx) {
        publicationRepository.deleteById(idx);
    }
}