package kr.ac.univ.handler;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.loginHistory.domain.LoginHistory;
import kr.ac.univ.loginHistory.domain.enums.AudLoginResultType;
import kr.ac.univ.loginHistory.repository.LoginHistoryRepository;
import kr.ac.univ.loginHistory.service.GeoLocationService;
import kr.ac.univ.util.IpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Configuration
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final LoginHistoryRepository loginHistoryRepository;
    private final GeoLocationService geoLocationService;

    public CustomAuthenticationFailureHandler(LoginHistoryRepository loginHistoryRepository, GeoLocationService geoLocationService) {
        this.loginHistoryRepository = loginHistoryRepository;
        this.geoLocationService = geoLocationService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 로그인 실패시 기능 구현
        String username = request.getParameter("username");
        String errorMsg = null;
        // ip 주소: ip 주소를 받기 위한 HttpServletRequest 객체
        String ip = IpUtil.getClientIP(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
        // GeoLite2를 사용한 location 조회
        String location = geoLocationService.getLocationByIp(ip);

        if (exception instanceof BadCredentialsException) {
            errorMsg = "The ID or password does not match.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMsg = "The authentication is not processed due to system problem that occurred internally.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMsg = "The authentication is not found.";
        } else if (exception instanceof DisabledException) {
            errorMsg = "This user is inactive.";
        }  else if (exception instanceof LockedException) {
            errorMsg = "This user is need to the admin's authentication.";
        }  else {
            errorMsg = "Please contact admin.";
        }

        System.out.println("로그인 실패!");

        loginHistoryRepository.save(LoginHistory.builder()
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .createdBy(username)
                .lastModifiedBy(username)
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(null)
                .audIp(ip)
                .audLocation(location)
                .audMessage(errorMsg)
                .audLoginResultType(AudLoginResultType.FAIL).build());

        request.setAttribute("username", username);
        request.setAttribute("errorMsg", errorMsg);

        request.getRequestDispatcher("/user/login/fail").forward(request, response);
    }

}