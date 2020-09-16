package kr.ac.univ.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;

public class AccessCheck {
    /**
     * [module-app-admin에서 사용자 권한에 따른 접근 가능 여부]
     * <p>
     * root: 모든 권한에 대한 접근 허용
     * manager: general 권한 접근 허용, manager 권한의 사용자가 같은 경우 접근 허용
     *
     * @param createdBy
     * @return
     */
    public static Boolean isAccessInModuleAdmin(String createdBy) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        boolean result = false;

        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            switch (grantedAuthority.getAuthority()) {
                case "root":
                    result = true;
                    break;
                case "manager":
                    if ("root".equals(createdBy)) result = false;
                        // Authority가 MANAGER, 작성자와 username이 같은 경우 접근 허용
                    else if (username.equals(createdBy)) result = true;
                        // Authority가 MANAGER, 작성자와 username이 다른 경우 접근 불가
                    else if (!username.equals(createdBy)) result = false;
                    else result = true;
                    break;
                default:
                    result = false;
                    break;
            }
        }

        return result;
    }

    /**
     * [module-app-web에서 사용자 권한에 따른 접근 가능 여부]
     * <p>
     * 비인증 사용자인 경우 접근 불가
     * 생성자와 사용자 아이디가 같은 경우 접근 허용
     *
     * @param createdBy
     * @return
     */
    public static Boolean isAccessInModuleWeb(String createdBy) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean result = false;

        // 비인증 사용자, 인증이 안된 경우, authentication 객체가 null인 경우 false 반환
        if ("anonymousUser".equals(authentication.getPrincipal()) || !authentication.isAuthenticated() || EmptyUtil.isEmpty(authentication)) {
            result = false;
        } else {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            if (username.equals(createdBy)) {
                result = true;
            } else {
                result = false;
            }
        }

        return result;
    }
}