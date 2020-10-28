package kr.ac.univ.loginHistory.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.loginHistory.domain.LoginHistory;
import kr.ac.univ.loginHistory.domain.enums.AudLoginResultType;
import kr.ac.univ.loginHistory.dto.LoginHistoryDto;
import kr.ac.univ.loginHistory.dto.mapper.LoginHistoryMapper;
import kr.ac.univ.loginHistory.repository.LoginHistoryRepository;
import kr.ac.univ.loginHistory.repository.LoginHistoryRepositoryImpl;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;



@Service
public class LoginHistoryService {
    private final LoginHistoryRepository loginHistoryRepository;
    private final LoginHistoryRepositoryImpl loginHistoryRepositoryImpl;

    public LoginHistoryService(LoginHistoryRepository loginHistoryRepository, LoginHistoryRepositoryImpl loginHistoryRepositoryImpl) {
        this.loginHistoryRepository = loginHistoryRepository;
        this.loginHistoryRepositoryImpl = loginHistoryRepositoryImpl;
    }

    public Page<LoginHistoryDto> findLoginHistoryList(Pageable pageable, SearchDto searchDto) {
        Page<LoginHistory> loginHistoryList = null;
        Page<LoginHistoryDto> loginHistoryDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "AUD_MESSAGE":
                loginHistoryList = loginHistoryRepository.findAllByAudMessageContaining(pageable, searchDto.getKeyword());
                break;
            case "AUD_IP":
                loginHistoryList = loginHistoryRepository.findAllByAudIpContaining(pageable, searchDto.getKeyword());
                break;
            case "AUD_LOCATION":
                loginHistoryList = loginHistoryRepository.findAllByAudLocationContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                loginHistoryList = loginHistoryRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            case "AUD_LOGIN_RESULT_TYPE":
                System.out.println("======");
                System.out.println(AudLoginResultType.valueOf(searchDto.getKeyword()));
                System.out.println(searchDto.getKeyword());
                loginHistoryList = loginHistoryRepository.findAllByAudLoginResultTypeIs(pageable, AudLoginResultType.valueOf(searchDto.getKeyword()));
                break;
            default:
                loginHistoryList = loginHistoryRepository.findAll(pageable);
                break;
        }

        loginHistoryDtoList = new PageImpl<LoginHistoryDto>(LoginHistoryMapper.INSTANCE.toDto(loginHistoryList.getContent()), pageable, loginHistoryList.getTotalElements());

        // NewIcon 판별
        for (LoginHistoryDto loginHistoryDto : loginHistoryDtoList) {
            loginHistoryDto.setNewIcon(NewIconCheck.isNew(loginHistoryDto.getCreatedDate()));
        }

        return loginHistoryDtoList;
    }

    public Long countLoginHistoryByDays(Long days) {
        return loginHistoryRepository.countAllByCreatedDateBetween(LocalDateTime.now().minusDays(days), LocalDateTime.now());
    }

    public List<LoginHistoryDto> findLoginHistoryList() {
        List<LoginHistoryDto> loginHistoryDtoList = LoginHistoryMapper.INSTANCE.toDto(loginHistoryRepository.findTop10ByOrderByIdxDesc());

        // NewIcon 판별
        for (LoginHistoryDto loginHistoryDto : loginHistoryDtoList) {
            loginHistoryDto.setNewIcon(NewIconCheck.isNew(loginHistoryDto.getCreatedDate()));
        }

        return loginHistoryDtoList;
    }

    public LoginHistoryDto findLoginHistoryByIdx(Long idx) {
        LoginHistoryDto loginHistoryDto = LoginHistoryMapper.INSTANCE.toDto(loginHistoryRepository.findById(idx).orElse(new LoginHistory()));

        // 권한 설정
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        if (AccessCheck.isAccessInModuleWeb(loginHistoryDto.getCreatedBy())) {
            loginHistoryDto.setAccess(true);
        } else {
            loginHistoryDto.setAccess(false);
        }

        return loginHistoryDto;
    }

    public Long updateLoginHistory(Long idx, ActiveStatus activeStatus) {
        return loginHistoryRepositoryImpl.updateActiveStatusByIdx(idx, activeStatus);
    }

    public void deleteLoginHistoryByIdx(Long idx) {
        loginHistoryRepository.deleteById(idx);
    }
}