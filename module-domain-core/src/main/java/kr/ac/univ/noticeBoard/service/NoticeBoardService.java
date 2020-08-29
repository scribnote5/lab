package kr.ac.univ.noticeBoard.service;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.noticeBoard.dto.NoticeBoardDto;
import kr.ac.univ.noticeBoard.dto.mapper.NoticeBoardMapper;
import kr.ac.univ.noticeBoard.repository.NoticeBoardRepository;
import kr.ac.univ.noticeBoard.repository.NoticeBoardRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;
    private final NoticeBoardRepositoryImpl noticeBoardRepositoryImpl;
    private final UserRepository userRepository;

    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository, NoticeBoardRepositoryImpl noticeBoardRepositoryImpl, UserRepository userRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.noticeBoardRepositoryImpl = noticeBoardRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<NoticeBoardDto> findNoticeBoardList(Pageable pageable, SearchDto searchDto) {
        Page<NoticeBoard> noticeBoardList = null;
        Page<NoticeBoardDto> noticeBoardDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                noticeBoardList = noticeBoardRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                break;
            case "CONTENT":
                noticeBoardList = noticeBoardRepository.findAllByContentContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                noticeBoardList = noticeBoardRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            default:
                noticeBoardList = noticeBoardRepository.findAll(pageable);
                break;
        }

        noticeBoardDtoList = new PageImpl<NoticeBoardDto>(NoticeBoardMapper.INSTANCE.toDto(noticeBoardList.getContent()), pageable, noticeBoardList.getTotalElements());

        // NewIcon 판별
        for (NoticeBoardDto noticeBoardDto : noticeBoardDtoList) {
            noticeBoardDto.setNewIcon(NewIconCheck.isNew(noticeBoardDto.getCreatedDate()));
        }

        return noticeBoardDtoList;
    }

    public Long insertNoticeBoard(NoticeBoardDto noticeBoardDto) {

        return noticeBoardRepository.save(NoticeBoardMapper.INSTANCE.toEntity(noticeBoardDto)).getIdx();
    }

    public NoticeBoardDto findNoticeBoardByIdx(Long idx) {
        NoticeBoardDto noticeBoardDto = NoticeBoardMapper.INSTANCE.toDto(noticeBoardRepository.findById(idx).orElse(new NoticeBoard()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            noticeBoardDto.setAccess(true);
        }
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        else if (AccessCheck.isAccess(noticeBoardDto.getCreatedBy(), userRepository.findByUsername(noticeBoardDto.getCreatedBy()).getAuthorityType().getAuthorityType())) {
            noticeBoardDto.setAccess(true);
        } else {
            noticeBoardDto.setAccess(false);
        }

        noticeBoardRepositoryImpl.updateViewCountByIdx(idx);

        return noticeBoardDto;
    }

    @Transactional
    public Long updateNoticeBoard(Long idx, NoticeBoardDto noticeBoardDto) {
        NoticeBoard persistNoticeBoard = noticeBoardRepository.getOne(idx);
        NoticeBoard noticeBoard = NoticeBoardMapper.INSTANCE.toEntity(noticeBoardDto);

        persistNoticeBoard.update(noticeBoard);

        return noticeBoardRepository.save(noticeBoard).getIdx();
    }

    public void deleteNoticeBoardByIdx(Long idx) {
        noticeBoardRepository.deleteById(idx);
    }
}