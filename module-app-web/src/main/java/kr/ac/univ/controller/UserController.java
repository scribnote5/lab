package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.user.dto.UserDto;
import kr.ac.univ.user.dto.UserPrincipal;
import kr.ac.univ.user.dto.mapper.UserMapper;
import kr.ac.univ.user.service.UserAttachedFileService;
import kr.ac.univ.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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
    public String index(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        model.addAttribute("userDto", userPrincipal);

        return "/user/index";
    }

    //Login Page
    @GetMapping("/login")
    public String login() {

        return "/user/login";
    }

    // Login Fail
    @PostMapping("/login/fail")
    public String loginFail(HttpServletRequest request, String errormsg) {

        return "/user/login";
    }

    // Logout
    @GetMapping("/logout/success")
    public String logout() {

        return "/user/logoutSuccess";
    }

    // Permission Denied
    @GetMapping("/permission-denied")
    public String permissionDenied() {

        return "/user/permission-denied";
    }

    // List
    @GetMapping("/list")
    public String noticeBoardList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("userDtoList", userService.findUserList(pageable, searchDto));

        return "/user/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String loginForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        UserDto userDto = null;
        userDto = UserMapper.INSTANCE.toDto(userService.findUserByIdx(idx));
        userDto = UserMapper.INSTANCE.toDto(userDto, userAttachedFileService.findAttachedFileByUserIdx(idx));

        model.addAttribute("userDto", userDto);

        return "/user/form";
    }

    // Read
    @GetMapping({"", "/"})
    public String noticeBoardRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        UserDto userDto = null;
        userDto = UserMapper.INSTANCE.toDto(userService.findUserByIdx(idx));
        userDto = UserMapper.INSTANCE.toDto(userDto, userAttachedFileService.findAttachedFileByUserIdx(idx));

        model.addAttribute("userDto", userDto);

        return "/user/read";
    }
}