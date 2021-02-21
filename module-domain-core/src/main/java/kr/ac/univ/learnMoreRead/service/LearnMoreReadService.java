package kr.ac.univ.learnMoreRead.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.learnMoreRead.domain.LearnMoreRead;
import kr.ac.univ.learnMoreRead.dto.LearnMoreReadDto;
import kr.ac.univ.learnMoreRead.dto.mapper.LearnMoreReadMapper;
import kr.ac.univ.learnMoreRead.repository.LearnMoreReadRepository;
import kr.ac.univ.learnMoreRead.repository.LearnMoreReadRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LearnMoreReadService {
    private final LearnMoreReadRepository learnMoreReadRepository;
    private final LearnMoreReadRepositoryImpl learnMoreReadRepositoryImpl;
    private final UserRepository userRepository;

    public LearnMoreReadService(LearnMoreReadRepository learnMoreReadRepository, LearnMoreReadRepositoryImpl learnMoreReadRepositoryImpl, UserRepository userRepository) {
        this.learnMoreReadRepository = learnMoreReadRepository;
        this.learnMoreReadRepositoryImpl = learnMoreReadRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<LearnMoreReadDto> findLearnMoreList(Pageable pageable, SearchDto searchDto) {
        Page<LearnMoreRead> learnMoreReadList = null;
        Page<LearnMoreReadDto> learnMoreReadDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                learnMoreReadList = learnMoreReadRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                learnMoreReadList = learnMoreReadRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            default:
                learnMoreReadList = learnMoreReadRepository.findAll(pageable);
                break;
        }

        learnMoreReadDtoList = new PageImpl<>(LearnMoreReadMapper.INSTANCE.toDto(learnMoreReadList.getContent()), pageable, learnMoreReadList.getTotalElements());

        // NewIcon 판별
        for (LearnMoreReadDto learnMoreReadDto : learnMoreReadDtoList) {
            learnMoreReadDto.setNewIcon(NewIconCheck.isNew(learnMoreReadDto.getCreatedDate()));
        }

        return learnMoreReadDtoList;
    }

    public List<LearnMoreReadDto> findAllByActiveStatusIs() {
        return LearnMoreReadMapper.INSTANCE.toDto(learnMoreReadRepository.findAllByActiveStatusIs(ActiveStatus.ACTIVE));
    }

    public Long countAllByActiveStatus() {
        return learnMoreReadRepository.countAllByActiveStatus(ActiveStatus.ACTIVE);
    }

    public Long insertLearnMore(LearnMoreReadDto learnMoreReadDto) {
        return learnMoreReadRepository.save(LearnMoreReadMapper.INSTANCE.toEntity(learnMoreReadDto)).getIdx();
    }

    public LearnMoreReadDto findLearnMoreByIdx(Long idx) {
        LearnMoreReadDto learnMoreReadDto = LearnMoreReadMapper.INSTANCE.toDto(learnMoreReadRepository.findById(idx).orElse(new LearnMoreRead()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            learnMoreReadDto.setAccess(true);
        }
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        else {
            learnMoreReadDto.setAccess(AccessCheck.isAccessInGeneral(learnMoreReadDto.getCreatedBy(), userRepository.findByUsername(learnMoreReadDto.getCreatedBy()).getAuthorityType().name()));
        }

        learnMoreReadRepositoryImpl.updateViewsByIdx(idx);
        learnMoreReadDto.setViews(learnMoreReadDto.getViews() + 1);

        return learnMoreReadDto;
    }

    @Transactional
    public Long updateLearnMore(Long idx, LearnMoreReadDto learnMoreReadDto) {
        LearnMoreRead persistLearnMoreRead = learnMoreReadRepository.getOne(idx);
        LearnMoreRead learnMoreRead = LearnMoreReadMapper.INSTANCE.toEntity(learnMoreReadDto);

        persistLearnMoreRead.update(learnMoreRead);

        return learnMoreReadRepository.save(persistLearnMoreRead).getIdx();
    }

    public void deleteLearnMoreByIdx(Long idx) {
        learnMoreReadRepository.deleteById(idx);
    }
}