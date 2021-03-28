package kr.ac.univ.guestBook.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.guestBook.domain.GuestBook;
import kr.ac.univ.guestBook.dto.GuestBookDto;
import kr.ac.univ.guestBook.dto.mapper.GuestBookMapper;
import kr.ac.univ.guestBook.repository.GuestBookRepository;
import kr.ac.univ.guestBook.repository.GuestBookRepositoryImpl;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.EmptyUtil;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GuestBookService {
    @Value("${module.name}")
    private String moduleName;
    private final GuestBookRepository guestBookRepository;
    private final GuestBookRepositoryImpl guestBookRepositoryImpl;
    private final UserRepository userRepository;

    public GuestBookService(GuestBookRepository guestBookRepository, GuestBookRepositoryImpl guestBookRepositoryImpl, UserRepository userRepository) {
        this.guestBookRepository = guestBookRepository;
        this.guestBookRepositoryImpl = guestBookRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<GuestBookDto> findGuestBookList(Pageable pageable, SearchDto searchDto) {
        Page<GuestBook> guestBookList = null;
        Page<GuestBookDto> guestBookDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                if ("module-app-admin".equals(moduleName)) {
                    guestBookList = guestBookRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    guestBookList = guestBookRepository.findAllByTitleContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    guestBookList = null;
                }
                break;
            case "CONTENT":
                if ("module-app-admin".equals(moduleName)) {
                    guestBookList = guestBookRepository.findAllByContentContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    guestBookList = guestBookRepository.findAllByContentContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    guestBookList = null;
                }
                break;
            case "ID":
                if ("module-app-admin".equals(moduleName)) {
                    guestBookList = guestBookRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    guestBookList = guestBookRepository.findAllByCreatedByContainingAndActiveStatusIs(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    guestBookList = null;
                }
                break;
            default:
                if ("module-app-admin".equals(moduleName)) {
                    guestBookList = guestBookRepository.findAllByOrderByMainPagePriorityAsc(pageable);
                } else if ("module-app-web".equals(moduleName)) {
                    guestBookList = guestBookRepository.findAllByActiveStatusIsOrderByMainPagePriorityAsc(pageable, ActiveStatus.ACTIVE);
                } else {
                    guestBookList = null;
                }
                break;
        }

        guestBookDtoList = new PageImpl<>(GuestBookMapper.INSTANCE.toDto(guestBookList.getContent()), pageable, guestBookList.getTotalElements());

        // NewIcon 판별
        for (GuestBookDto guestBookDto : guestBookDtoList) {
            guestBookDto.setNewIcon(NewIconCheck.isNew(guestBookDto.getCreatedDate()));
        }

        return guestBookDtoList;
    }

    public List<GuestBookDto> findTop10ByOrderByIdxDesc() {
        List<GuestBookDto> guestBookDtoList = GuestBookMapper.INSTANCE.toDto(guestBookRepository.findTop10ByOrderByMainPagePriorityAscIdxDesc());

        // NewIcon 판별
        for (GuestBookDto guestBookDto : guestBookDtoList) {
            guestBookDto.setNewIcon(NewIconCheck.isNew(guestBookDto.getCreatedDate()));
        }

        return guestBookDtoList;
    }

    public List<GuestBookDto> findAllByActiveStatusIsAndMainPagePriorityLessThanEqualOrderByMainPagePriorityAsc() {
        return GuestBookMapper.INSTANCE.toDto(guestBookRepository.findAllByActiveStatusIsAndMainPagePriorityLessThanEqualOrderByMainPagePriorityAsc(ActiveStatus.ACTIVE, 10L));
    }

    public Long insertGuestBook(GuestBookDto guestBookDto) {
        return guestBookRepository.save(GuestBookMapper.INSTANCE.toEntity(guestBookDto)).getIdx();
    }

    public GuestBookDto findGuestBookByIdx(Long idx) {
        GuestBookDto guestBookDto = GuestBookMapper.INSTANCE.toDto(guestBookRepository.findById(idx).orElse(new GuestBook()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            guestBookDto.setAccess(true);
        }
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        // 탈퇴 회원은 권한을 general로 설정 후 권한을 검사함
        else {
            User user = userRepository.findByUsername(guestBookDto.getCreatedBy());

            guestBookDto.setAccess(AccessCheck.isAccessInGeneral(guestBookDto.getCreatedBy(), EmptyUtil.isEmpty(user) ? "general" : user.getAuthorityType().getAuthorityType()));
        }

        guestBookRepositoryImpl.updateViewsByIdx(idx);
        guestBookDto.setViews(guestBookDto.getViews() + 1);

        return guestBookDto;
    }

    @Transactional
    public Long updateGuestBook(Long idx, GuestBookDto guestBookDto) {
        GuestBook persistGuestBook = guestBookRepository.getOne(idx);
        GuestBook guestBook = GuestBookMapper.INSTANCE.toEntity(guestBookDto);

        persistGuestBook.update(guestBook);

        return guestBookRepository.save(persistGuestBook).getIdx();
    }

    public void deleteGuestBookByIdx(Long idx) {
        guestBookRepository.deleteById(idx);
    }
}