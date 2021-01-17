package kr.ac.univ.noticeBoard.service;

import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.noticeBoard.domain.NoticeBoardComment;
import kr.ac.univ.noticeBoard.dto.NoticeBoardCommentDto;
import kr.ac.univ.noticeBoard.dto.mapper.NoticeBoardCommentMapper;
import kr.ac.univ.noticeBoard.repository.NoticeBoardCommentRepository;
import kr.ac.univ.noticeBoard.repository.NoticeBoardCommentRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NoticeBoardCommentService {
    private final NoticeBoardCommentRepository noticeBoardCommentRepository;
    private final NoticeBoardCommentRepositoryImpl noticeBoardCommentRepositoryImpl;
    private final UserRepository userRepository;

    public NoticeBoardCommentService(NoticeBoardCommentRepository noticeBoardCommentRepository, NoticeBoardCommentRepositoryImpl noticeBoardCommentRepositoryImpl, UserRepository userRepository) {
        this.noticeBoardCommentRepository = noticeBoardCommentRepository;
        this.noticeBoardCommentRepositoryImpl = noticeBoardCommentRepositoryImpl;
        this.userRepository = userRepository;
    }

    public List<NoticeBoardCommentDto> findAllByNoticeBoardIdxOrderByCreatedDateDesc(Long noticeBoardIdx) {
        List<NoticeBoardComment> noticeBoardCommentList = null;
        List<NoticeBoardCommentDto> noticeBoardCommentDtoList = null;

        noticeBoardCommentList = noticeBoardCommentRepository.findAllByNoticeBoardIdxOrderByCreatedDateDesc(noticeBoardIdx);

        // NoticeBoard -> NoticeBoardDto
        noticeBoardCommentDtoList = NoticeBoardCommentMapper.INSTANCE.toDto(noticeBoardCommentList);

        for (NoticeBoardCommentDto noticeBoardCommentDto : noticeBoardCommentDtoList) {
            // NewIcon 판별
            noticeBoardCommentDto.setNewIcon(NewIconCheck.isNew(noticeBoardCommentDto.getCreatedDate()));

            // 권한 설정
            if (AccessCheck.isAccessInModuleWeb(noticeBoardCommentDto.getCreatedBy())) {
                noticeBoardCommentDto.setAccess(true);
            } else {
                noticeBoardCommentDto.setAccess(false);
            }
        }

        return noticeBoardCommentDtoList;
    }

    public Long insertNoticeBoardComment(NoticeBoardComment noticeBoardComment) {

        return noticeBoardCommentRepository.save(noticeBoardComment).getIdx();
    }

    @Transactional
    public Long updateNoticeBoardComment(Long idx, NoticeBoardCommentDto noticeBoardCommentDto) {
        NoticeBoardComment persistNoticeBoardComment = noticeBoardCommentRepository.getOne(idx);
        NoticeBoardComment noticeBoardComment = NoticeBoardCommentMapper.INSTANCE.toEntity(noticeBoardCommentDto);

        persistNoticeBoardComment.update(noticeBoardComment);

        return noticeBoardCommentRepository.save(persistNoticeBoardComment).getIdx();
    }


    public void deleteNoticeBoardCommentByIdx(Long idx) {
        noticeBoardCommentRepository.deleteById(idx);
    }

    public void deleteAllByNoticeBoardIdx(Long idx) {
        noticeBoardCommentRepositoryImpl.deleteAllByNoticeBoardIdx(idx);
    }

}