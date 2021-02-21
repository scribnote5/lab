package kr.ac.univ.learnMoreVideo.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.learnMoreVideo.domain.LearnMoreVideo;
import kr.ac.univ.learnMoreVideo.dto.LearnMoreVideoDto;
import kr.ac.univ.learnMoreVideo.dto.mapper.LearnMoreVideoMapper;
import kr.ac.univ.learnMoreVideo.repository.LearnMoreVideoRepository;
import kr.ac.univ.learnMoreVideo.repository.LearnMoreVideoRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LearnMoreVideoService {
    private final LearnMoreVideoRepository learnMoreVideoRepository;
    private final LearnMoreVideoRepositoryImpl learnMoreVideoRepositoryImpl;
    private final UserRepository userRepository;

    public LearnMoreVideoService(LearnMoreVideoRepository learnMoreVideoRepository, LearnMoreVideoRepositoryImpl learnMoreVideoRepositoryImpl, UserRepository userRepository) {
        this.learnMoreVideoRepository = learnMoreVideoRepository;
        this.learnMoreVideoRepositoryImpl = learnMoreVideoRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<LearnMoreVideoDto> findLearnMoreList(Pageable pageable, SearchDto searchDto) {
        Page<LearnMoreVideo> learnMoreVideoList = null;
        Page<LearnMoreVideoDto> learnMoreVideoDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                learnMoreVideoList = learnMoreVideoRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                learnMoreVideoList = learnMoreVideoRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            default:
                learnMoreVideoList = learnMoreVideoRepository.findAll(pageable);
                break;
        }

        learnMoreVideoDtoList = new PageImpl<>(LearnMoreVideoMapper.INSTANCE.toDto(learnMoreVideoList.getContent()), pageable, learnMoreVideoList.getTotalElements());

        // NewIcon 판별
        for (LearnMoreVideoDto learnMoreVideoDto : learnMoreVideoDtoList) {
            learnMoreVideoDto.setNewIcon(NewIconCheck.isNew(learnMoreVideoDto.getCreatedDate()));
        }

        return learnMoreVideoDtoList;
    }

    public List<LearnMoreVideoDto> findAllByActiveStatusIs() {
        return LearnMoreVideoMapper.INSTANCE.toDto(learnMoreVideoRepository.findAllByActiveStatusIs(ActiveStatus.ACTIVE));
    }

    public Long countAllByActiveStatus() {
        return learnMoreVideoRepository.countAllByActiveStatus(ActiveStatus.ACTIVE);
    }

    public Long insertLearnMore(LearnMoreVideoDto learnMoreVideoDto) {
        return learnMoreVideoRepository.save(LearnMoreVideoMapper.INSTANCE.toEntity(learnMoreVideoDto)).getIdx();
    }

    public LearnMoreVideoDto findLearnMoreByIdx(Long idx) {
        LearnMoreVideoDto learnMoreVideoDto = LearnMoreVideoMapper.INSTANCE.toDto(learnMoreVideoRepository.findById(idx).orElse(new LearnMoreVideo()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            learnMoreVideoDto.setAccess(true);
        }
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        else {
            learnMoreVideoDto.setAccess(AccessCheck.isAccessInGeneral(learnMoreVideoDto.getCreatedBy(), userRepository.findByUsername(learnMoreVideoDto.getCreatedBy()).getAuthorityType().name()));
        }

        learnMoreVideoRepositoryImpl.updateViewsByIdx(idx);
        learnMoreVideoDto.setViews(learnMoreVideoDto.getViews() + 1);

        return learnMoreVideoDto;
    }

    @Transactional
    public Long updateLearnMore(Long idx, LearnMoreVideoDto learnMoreVideoDto) {
        LearnMoreVideo persistLearnMoreVideo = learnMoreVideoRepository.getOne(idx);
        LearnMoreVideo learnMoreVideo = LearnMoreVideoMapper.INSTANCE.toEntity(learnMoreVideoDto);

        persistLearnMoreVideo.update(learnMoreVideo);

        return learnMoreVideoRepository.save(persistLearnMoreVideo).getIdx();
    }

    public void deleteLearnMoreByIdx(Long idx) {
        learnMoreVideoRepository.deleteById(idx);
    }
}