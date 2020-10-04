package kr.ac.univ.learnMore.service;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.learnMore.domain.LearnMore;
import kr.ac.univ.learnMore.domain.enums.DownloadFileType;
import kr.ac.univ.learnMore.dto.LearnMoreDto;
import kr.ac.univ.learnMore.dto.mapper.LearnMoreMapper;
import kr.ac.univ.learnMore.repository.LearnMoreRepository;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LearnMoreService {
    private final LearnMoreRepository learnMoreRepository;
    private final UserRepository userRepository;

    public LearnMoreService(LearnMoreRepository learnMoreRepository, UserRepository userRepository) {
        this.learnMoreRepository = learnMoreRepository;
        this.userRepository = userRepository;
    }

    public Page<LearnMoreDto> findLearnMoreList(Pageable pageable, SearchDto searchDto) {
        Page<LearnMore> learnMoreList = null;
        Page<LearnMoreDto> learnMoreDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                learnMoreList = learnMoreRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                learnMoreList = learnMoreRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            case "DOWNLOAD_FILE_TYPE":
                learnMoreList = learnMoreRepository.findAllByDownloadFileTypeIs(pageable, DownloadFileType.valueOf(searchDto.getKeyword()));
                break;
            default:
                learnMoreList = learnMoreRepository.findAll(pageable);
                break;
        }

        learnMoreDtoList = new PageImpl<LearnMoreDto>(LearnMoreMapper.INSTANCE.toDto(learnMoreList.getContent()), pageable, learnMoreList.getTotalElements());

        // NewIcon 판별
        for (LearnMoreDto learnMoreDto : learnMoreDtoList) {
            learnMoreDto.setNewIcon(NewIconCheck.isNew(learnMoreDto.getCreatedDate()));
        }

        return learnMoreDtoList;
    }

    public Long insertLearnMore(LearnMoreDto learnMoreDto) {

        return learnMoreRepository.save(LearnMoreMapper.INSTANCE.toEntity(learnMoreDto)).getIdx();
    }

    public LearnMoreDto findLearnMoreByIdx(Long idx) {
        LearnMoreDto learnMoreDto = LearnMoreMapper.INSTANCE.toDto(learnMoreRepository.findById(idx).orElse(new LearnMore()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            learnMoreDto.setAccess(true);
        }
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        else if (AccessCheck.isAccessInModuleWeb(learnMoreDto.getCreatedBy())) {
            learnMoreDto.setAccess(true);
        } else {
            learnMoreDto.setAccess(false);
        }

        return learnMoreDto;
    }

    @Transactional
    public Long updateLearnMore(Long idx, LearnMoreDto learnMoreDto) {
        LearnMore persistLearnMore = learnMoreRepository.getOne(idx);
        LearnMore learnMore = LearnMoreMapper.INSTANCE.toEntity(learnMoreDto);

        persistLearnMore.update(learnMore);

        return learnMoreRepository.save(learnMore).getIdx();
    }

    public void deleteLearnMoreByIdx(Long idx) {
        learnMoreRepository.deleteById(idx);
    }
}