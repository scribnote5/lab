package kr.ac.univ.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.loginHistory.domain.LoginHistory;
import kr.ac.univ.loginHistory.domain.enums.AudLoginResultType;
import kr.ac.univ.loginHistory.repository.LoginHistoryRepository;
import kr.ac.univ.loginHistory.service.GeoLocationService;
import kr.ac.univ.user.domain.enums.AuthorityType;
import kr.ac.univ.user.dto.UserPrincipal;
import kr.ac.univ.util.AudMessageUtil;
import kr.ac.univ.util.IpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Value("${module.name}")
    private String moduleName;
    private final LoginHistoryRepository loginHistoryRepository;
    private final GeoLocationService geoLocationService;

    public CustomAuthenticationSuccessHandler(LoginHistoryRepository loginHistoryRepository, GeoLocationService geoLocationService) {
        this.loginHistoryRepository = loginHistoryRepository;
        this.geoLocationService = geoLocationService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String url;

        // ip 주소: ip 주소를 받기 위한 HttpServletRequest 객체
        String ip = IpUtil.getClientIP(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
        // GeoLite2를 사용한 location 조회
        String location = geoLocationService.getLocationByIp(ip);

        loginHistoryRepository.save(LoginHistory.builder()
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .createdBy(userPrincipal.getUsername())
                .lastModifiedBy(userPrincipal.getUsername())
                .activeStatus(ActiveStatus.ACTIVE)
                .audIdx(userPrincipal.getIdx())
                .audIp(ip)
                .audLocation(location)
                .audMessage(AudMessageUtil.getLoginSuccessAudMessage(userPrincipal.getUsername()))
                .audLoginResultType(AudLoginResultType.SUCCESS).build());

        if ("module-app-admin".equals(moduleName)) {
            url = "/main/home";
        } else if ("module-app-web".equals(moduleName)) {
            url = "/";
        } else {
            url = null;
        }

        response.sendRedirect(url);
    }
}