package kr.ac.univ.noticeBoard.service;

import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.noticeBoard.dto.NoticeBoardDto;
import kr.ac.univ.noticeBoard.dto.mapper.NoticeBoardMapper;
import kr.ac.univ.noticeBoard.repository.NoticeBoardRepository;
import kr.ac.univ.noticeBoard.repository.NoticeBoardRepositoryImpl;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;
    private final NoticeBoardRepositoryImpl noticeBoardRepositoryImpl;

    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository, NoticeBoardRepositoryImpl noticeBoardRepositoryImpl) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.noticeBoardRepositoryImpl = noticeBoardRepositoryImpl;
    }

    public Page<NoticeBoardDto> findNoticeBoardList(Pageable pageable) {
        Page<NoticeBoard> noticeBoardList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");
        noticeBoardList = noticeBoardRepository.findAll(pageable);

        return new PageImpl<NoticeBoardDto>(NoticeBoardMapper.INSTANCE.toDto(noticeBoardList.getContent()), pageable, noticeBoardList.getTotalElements());
    }

    public Long insertNoticeBoard(NoticeBoard noticeBoard) {

        return noticeBoardRepository.save(noticeBoard).getIdx();
    }

    public NoticeBoardDto findNoticeBoardByIdx(Long idx) {
        noticeBoardRepositoryImpl.updateViewCountById(idx);

        return NoticeBoardMapper.INSTANCE.toDto(noticeBoardRepository.findById(idx).orElse(new NoticeBoard()));
    }

    @Transactional
    public Long updateNoticeBoard(Long idx, NoticeBoard noticeBoard) {
        noticeBoardRepository.getOne(idx).update(noticeBoard);

        return noticeBoardRepository.save(noticeBoard).getIdx();
    }

    public void deleteNoticeBoardByIdx(Long idx) {
        noticeBoardRepository.deleteById(idx);
    }
}