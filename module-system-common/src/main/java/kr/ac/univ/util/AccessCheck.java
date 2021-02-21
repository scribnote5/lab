package kr.ac.univ.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AccessCheck {
    /**
     * [일반적인 상황에서 사용자 권한에 따른 접근 가능 여부]
     *
     * 비인증 사용자인 경우 접근 불가
     * root: 모든 권한에 대한 접근 허용
     * manager: 생성자가 root인 경우 접근 허용, 로그인한 사용자의 username과 생성자가 같은 경우 접근 허용
     *
     * @param createdBy
     * @return
     */
    public static Boolean isAccessInGeneral(String createdBy, String authorityType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean result = false;

        // 비인증 사용자, 인증이 안된 경우, authentication 객체가 null인 경우
        // -> 접근 불가
        if ("anonymousUser".equals(authentication.getPrincipal()) || !authentication.isAuthenticated() || EmptyUtil.isEmpty(authentication)) {
            result = false;
        } else {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String authenticationUsername = userDetails.getUsername();

            for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
                switch (grantedAuthority.getAuthority()) {
                    // 로그인한 사용자의 권한: root
                    // -> 접근 가능
                    case "root":
                        result = true;
                        break;
                    case "manager":
                        // createdBy: root
                        // -> 접근 불가
                        if ("root".equals(createdBy)) {
                            result = false;
                        }
                        // username authority: MANAGER
                        // 로그인한 사용자의 username과 username: 다름
                        // -> 접근 불가
                        else if("MANAGER".equals(authorityType) && !authenticationUsername.equals(createdBy)) {
                            result = false;
                        }
                        // 나머지 조건
                        // -> 접근 가능
                        else {
                            result = true;
                        }
                        break;
                    default:
                        // 로그인한 사용자의 username과 createdBy: 같음
                        // -> 접근 가능
                        if (authenticationUsername.equals(createdBy)) {
                            result = true;
                        }
                        // 로그인한 사용자의 username과 createdBy: 다름
                        // -> 접근 불가
                        else {
                            result = false;
                        }
                        break;
                }
            }
        }

        return result;
    }

    /**
     * [module-app-admin user에서 사용자 권한에 따른 접근 가능 여부]
     *
     * 비인증 사용자인 경우 접근 불가
     * root: 모든 권한에 대한 접근 허용
     * manager: 생성자가 root인 경우 접근 허용, 로그인한 사용자의 username과 생성자가 같은 경우 접근 허용
     *
     * @param createdBy
     * @return
     */
    public static Boolean isAccessInModuleAdminUser(String createdBy, String username, String authorityType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean result = false;

        // 비인증 사용자, 인증이 안된 경우, authentication 객체가 null인 경우
        // -> 접근 불가
        if ("anonymousUser".equals(authentication.getPrincipal()) || !authentication.isAuthenticated() || EmptyUtil.isEmpty(authentication)) {
            result = false;
        } else {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String authenticationUsername = userDetails.getUsername();

            for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
                switch (grantedAuthority.getAuthority()) {
                    // 로그인한 사용자의 권한: root
                    // -> 접근 가능
                    case "root":
                        result = true;
                        break;
                    case "manager":
                        // createdBy: root
                        // username authority: MANAGER
                        // 로그인한 사용자의 username과 username: 같음
                        // -> 접근 가능
                        if ("root".equals(createdBy) && "MANAGER".equals(authorityType) && username.equals(authenticationUsername)) {
                            result = true;
                        }
                        // 로그인한 사용자의 username과 createdBy: 같음
                        // -> 접근 가능
                        else if (authenticationUsername.equals(createdBy)) {
                            result = true;
                        }
                        // 로그인한 사용자의 username과 createdBy: 다름
                        // -> 접근 불가
                        else {
                            result = false;
                        }
                        break;
                    default:
                        result = false;
                        break;
                }
            }
        }

        return result;
    }

    /**
     * [module-app-web user에서 사용자 권한에 따른 접근 가능 여부]
     *
     * 비인증 사용자인 경우 접근 불가
     * 생성자가 root인 경우 접근 허용
     * 생성자 권한이 MANAGER인 경우 접근 허용
     * 생성자와 사용자 아이디가 같은 경우 접근 허용
     *
     * @param createdBy
     * @return
     */
    public static Boolean isAccessInModuleWebUser(String createdBy, String username, String authorityType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean result = false;

        // 비인증 사용자, 인증이 안된 경우, authentication 객체가 null인 경우
        // -> 접근 불가
        if ("anonymousUser".equals(authentication.getPrincipal()) || !authentication.isAuthenticated() || EmptyUtil.isEmpty(authentication)) {
            result = false;
        } else {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String authenticationUsername = userDetails.getUsername();

            // createdBy: root
            // username authority: MANAGER
            // 로그인한 사용자의 username과 username: 같음
            // -> 접근 가능
            if ("root".equals(createdBy) || "MANAGER".equals(authorityType) && username.equals(authenticationUsername)) {
                result = true;
            }
            // 로그인한 사용자의 username과 createdBy: 같음
            // -> 접근 가능
            else if (authenticationUsername.equals(createdBy)) {
                result = true;
            } else {
                result = false;
            }
        }

        return result;
    }
}