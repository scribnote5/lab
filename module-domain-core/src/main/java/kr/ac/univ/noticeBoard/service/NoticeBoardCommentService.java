package kr.ac.univ.noticeBoard.service;

import kr.ac.univ.maintenance.dto.mapper.MaintenanceMapper;
import kr.ac.univ.noticeBoard.domain.NoticeBoardComment;
import kr.ac.univ.noticeBoard.dto.NoticeBoardCommentDto;
import kr.ac.univ.noticeBoard.dto.mapper.NoticeBoardCommentMapper;
import kr.ac.univ.noticeBoard.repository.NoticeBoardCommentRepository;
import kr.ac.univ.noticeBoard.repository.NoticeBoardCommentRepositoryImpl;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.EmptyUtil;
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
        List<NoticeBoardCommentDto> noticeBoardCommentDtoList = null;

        noticeBoardCommentDtoList = NoticeBoardCommentMapper.INSTANCE.toDto(noticeBoardCommentRepository.findAllByNoticeBoardIdxOrderByCreatedDateDesc(noticeBoardIdx));

        for (NoticeBoardCommentDto noticeBoardCommentDto : noticeBoardCommentDtoList) {
            // NewIcon 판별
            noticeBoardCommentDto.setNewIcon(NewIconCheck.isNew(noticeBoardCommentDto.getCreatedDate()));

            // 권한 설정
            // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
            // 탈퇴 회원은 권한을 general로 설정 후 권한을 검사함
            User user = userRepository.findByUsername(noticeBoardCommentDto.getCreatedBy());

            noticeBoardCommentDto.setAccess(AccessCheck.isAccessInGeneral(noticeBoardCommentDto.getCreatedBy(), EmptyUtil.isEmpty(user) ? "general" : user.getAuthorityType().getAuthorityType()));
        }

        return noticeBoardCommentDtoList;
    }

    public Long insertNoticeBoardComment(NoticeBoardCommentDto noticeBoardCommentDto) {
        return noticeBoardCommentRepository.save(NoticeBoardCommentMapper.INSTANCE.toEntity(noticeBoardCommentDto)).getIdx();
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