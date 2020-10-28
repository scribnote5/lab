package kr.ac.univ.controller;

import kr.ac.univ.dataHistory.service.DataHistoryService;
import kr.ac.univ.loginHistory.service.LoginHistoryService;
import kr.ac.univ.noticeBoard.service.NoticeBoardService;
import kr.ac.univ.user.service.UserService;
import kr.ac.univ.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {
    private final DataHistoryService dataHistoryService;
    private final LoginHistoryService loginHistoryService;
    private final NoticeBoardService noticeBoardService;
    private final UserService userService;

    public MainController(DataHistoryService dataHistoryService, LoginHistoryService loginHistoryService, NoticeBoardService noticeBoardService, UserService userService) {
        this.dataHistoryService = dataHistoryService;
        this.loginHistoryService = loginHistoryService;
        this.noticeBoardService = noticeBoardService;
        this.userService = userService;
    }

    // Home
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("dataHistoryDtoList", dataHistoryService.findDataHistoryList());
        model.addAttribute("loginHistoryDtoList", loginHistoryService.findLoginHistoryList());
        model.addAttribute("noticeBoardDtoList", noticeBoardService.findNoticeBoardList());

        model.addAttribute("userCount", userService.countUser());
        model.addAttribute("dataHistoryCount", dataHistoryService.countDataHistory());

        model.addAttribute("countLoginUserBeforeOneday", loginHistoryService.countLoginHistoryByDays(1L));
        model.addAttribute("countLoginUserBeforeSevenDays", loginHistoryService.countLoginHistoryByDays(7L));
        model.addAttribute("countLoginUserBeforeTwentyEightDays", loginHistoryService.countLoginHistoryByDays(7L));

        model.addAttribute("countDataHistoryBeforeOneday", dataHistoryService.countLoginHistoryByDays(1L));
        model.addAttribute("countDataHistoryBeforeSevenDays", dataHistoryService.countLoginHistoryByDays(7L));
        model.addAttribute("countDataHistoryBeforeTwentyEightDays", dataHistoryService.countLoginHistoryByDays(28L));

        model.addAttribute("diskInfoList", FileUtil.getDiskInfo());

        return "/main/home";
    }
}