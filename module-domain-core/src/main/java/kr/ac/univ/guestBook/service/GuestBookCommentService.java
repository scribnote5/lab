package kr.ac.univ.guestBook.service;

import kr.ac.univ.guestBook.domain.GuestBookComment;
import kr.ac.univ.guestBook.dto.GuestBookCommentDto;
import kr.ac.univ.guestBook.dto.mapper.GuestBookCommentMapper;
import kr.ac.univ.guestBook.repository.GuestBookCommentRepository;
import kr.ac.univ.guestBook.repository.GuestBookCommentRepositoryImpl;
import kr.ac.univ.maintenance.dto.mapper.MaintenanceCommentMapper;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GuestBookCommentService {
    private final GuestBookCommentRepository guestBookCommentRepository;
    private final GuestBookCommentRepositoryImpl guestBookCommentRepositoryImpl;
    private final UserRepository userRepository;

    public GuestBookCommentService(GuestBookCommentRepository guestBookCommentRepository, GuestBookCommentRepositoryImpl guestBookCommentRepositoryImpl, UserRepository userRepository) {
        this.guestBookCommentRepository = guestBookCommentRepository;
        this.guestBookCommentRepositoryImpl = guestBookCommentRepositoryImpl;
        this.userRepository = userRepository;
    }

    public List<GuestBookCommentDto> findAllByGuestBookIdxOrderByCreatedDateDesc(Long guestBookIdx) {
        List<GuestBookCommentDto> guestBookCommentDtoList = null;

        guestBookCommentDtoList = GuestBookCommentMapper.INSTANCE.toDto(guestBookCommentRepository.findAllByGuestBookIdxOrderByCreatedDateDesc(guestBookIdx));

        for (GuestBookCommentDto guestBookCommentDto : guestBookCommentDtoList) {
            // NewIcon 판별
            guestBookCommentDto.setNewIcon(NewIconCheck.isNew(guestBookCommentDto.getCreatedDate()));

            // 권한 설정
            // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
            guestBookCommentDto.setAccess(AccessCheck.isAccessInGeneral(guestBookCommentDto.getCreatedBy(), userRepository.findByUsername(guestBookCommentDto.getCreatedBy()).getAuthorityType().name()));
        }

        return guestBookCommentDtoList;
    }

    public Long insertGuestBookComment(GuestBookCommentDto guestBookCommentDto) {
        return guestBookCommentRepository.save(GuestBookCommentMapper.INSTANCE.toEntity(guestBookCommentDto)).getIdx();
    }

    @Transactional
    public Long updateGuestBookComment(Long idx, GuestBookCommentDto guestBookCommentDto) {
        GuestBookComment persistGuestBookComment = guestBookCommentRepository.getOne(idx);
        GuestBookComment guestBookComment = GuestBookCommentMapper.INSTANCE.toEntity(guestBookCommentDto);

        persistGuestBookComment.update(guestBookComment);

        return guestBookCommentRepository.save(persistGuestBookComment).getIdx();
    }

    public void deleteGuestBookCommentByIdx(Long idx) {
        guestBookCommentRepository.deleteById(idx);
    }

    public void deleteAllByGuestBookIdx(Long idx) {
        guestBookCommentRepositoryImpl.deleteAllByGuestBookIdx(idx);
    }

}