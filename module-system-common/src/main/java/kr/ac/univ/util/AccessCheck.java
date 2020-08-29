package kr.ac.univ.util;

import kr.ac.univ.util.EmptyUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AccessCheck {
    /**
     * 사용자 권한에 따른 접근 허용
     * <p>
     * root: 모든 권한에 대한 접근 허용
     * manager: general 권한 접근 허용, manager 권한의 사용자가 같은 경우 접근 허용
     * general: general 권한의 사용자가 같은 경우 접근 허용
     * anonymous: 모든 접근 불가
     *
     * @param createdBy
     * @return
     */
    public static Boolean isAccess(String createdBy, String authorityType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean result = false;

        // 익명 사용자, 인증이 안된 경우, authentication 객체가 null인 경우 false 반환
        if ("anonymousUser".equals(authentication.getPrincipal()) || !authentication.isAuthenticated() || EmptyUtil.isEmpty(authentication)) {
            return false;
        } else {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
                switch (grantedAuthority.getAuthority()) {
                    case "root":
                        result = true;
                        break;
                    case "manager":
                        if ("root".equals(authorityType)) result = false;
                            // Authority가 MANAGER, 작성자와 username이 같은 경우 접근 허용
                        else if ("manager".equals(authorityType) && username.equals(createdBy)) result = true;
                            // Authority가 MANAGER, 작성자와 username이 다른 경우 접근 불가
                        else if ("manager".equals(authorityType) && !username.equals(createdBy)) result = false;
                        else result = true;
                        break;
                    case "general":
                        if (username.equals(createdBy)) result = true;
                        break;
                    default:
                        result = false;
                        break;
                }
            }
        }

        return result;
    }
}