package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.user.dto.UserDto;
import kr.ac.univ.user.dto.UserPrincipal;
import kr.ac.univ.user.service.UserAttachedFileService;
import kr.ac.univ.user.service.UserService;
import kr.ac.univ.util.EmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserAttachedFileService userAttachedFileService;

    public UserController(UserService userService, UserAttachedFileService userAttachedFileService) {
        this.userService = userService;
        this.userAttachedFileService = userAttachedFileService;
    }

    // Login Index
    @GetMapping("/index")
    public String index(Model model) {

        return "user/index";
    }

    // Login Page
    @GetMapping("/login")
    public String login(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        String returnPage = null;

        // 사용자가 로그인 안된 경우 /login 페이지로 이동
        if (EmptyUtil.isEmpty(userPrincipal)) {
            returnPage = "user/login";
        }
        // 사용자가 로그인한 경우 /main/home 페이지로 이동
        else {
            returnPage = "redirect:/main/home";
        }

        return returnPage;
    }

    // Login Fail
    @PostMapping("/login/fail")
    public String loginFail(HttpServletRequest request) {

        return "user/login";
    }

    // Logout
    @GetMapping("/logout/success")
    public RedirectView logout(Model model) {

        return new RedirectView("/user/login");
    }

    // Permission Denied
    @GetMapping("/permission-denied")
    public String permissionDenied() {

        return "user/access-denied";
    }

    // Access Denied
    @GetMapping("/access-denied")
    public String accessDenied() {

        return "user/access-denied";
    }

    // Anonymous User Permission Denied
    @GetMapping("/anonymous-user-permission-denied")
    public String anonymousUserPermissionDenied() {

        return "user/anonymous-user-permission-denied";
    }

    // List
    @GetMapping("/list")
    public String userList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("userDtoList", userService.findUserList(pageable, searchDto));

        return "user/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String loginForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        UserDto userDto = userService.findUserByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (userDto.isAccess()) {
            userDto = userAttachedFileService.findAttachedFileByUserIdx(idx, userDto);

            model.addAttribute("userDto", userDto);

            returnPage = "user/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String userRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        UserDto userDto = userService.findUserByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (userDto.isAccess()) {
            userDto = userAttachedFileService.findAttachedFileByUserIdx(idx, userDto);

            model.addAttribute("userDto", userDto);

            returnPage = "user/read";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }
}