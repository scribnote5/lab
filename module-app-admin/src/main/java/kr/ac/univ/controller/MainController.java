package kr.ac.univ.controller;

import kr.ac.univ.album.service.AlbumAttachedFileService;
import kr.ac.univ.album.service.AlbumService;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.user.domain.UserAttachedFile;
import kr.ac.univ.user.dto.UserDto;
import kr.ac.univ.user.service.UserAttachedFileService;
import kr.ac.univ.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {
    private final UserService userService;
    private final UserAttachedFileService userAttachedFileService;

    public MainController(UserService userService, UserAttachedFileService userAttachedFileService) {
        this.userService = userService;
        this.userAttachedFileService = userAttachedFileService;
    }

    // Home
    @GetMapping("/home")
    public String home( Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        UserDto userDto = userService.findUserByUsername(userDetails.getUsername());

//        userAttachedFileService.findAttachedFileByUserIdx(userDto.getIdx());




        return "/main/home";
    }
}