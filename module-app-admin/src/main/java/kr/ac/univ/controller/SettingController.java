package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.setting.dto.SettingDto;
import kr.ac.univ.setting.service.SettingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/setting")
public class SettingController {
    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    // Form
    @GetMapping("/form{idx}")
    public String settingForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        model.addAttribute("settingDto", settingService.findSettingByIdx(idx));

        return "setting/form";
    }

    // Read
    @GetMapping({"", "/"})
    public String settingRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        model.addAttribute("settingDto", settingService.findSettingByIdx(idx));

        return "setting/read";
    }
}