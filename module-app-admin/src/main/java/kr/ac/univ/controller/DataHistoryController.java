package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.dataHistory.dto.DataHistoryDto;
import kr.ac.univ.dataHistory.service.DataHistoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/data-history")
public class DataHistoryController {
    private final DataHistoryService dataHistoryService;

    public DataHistoryController(DataHistoryService dataHistoryService) {
        this.dataHistoryService = dataHistoryService;
    }

    // List
    @GetMapping("/list")
    public String dataHistoryList(Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("dataHistoryDtoList", dataHistoryService.findDataHistoryList(pageable, searchDto));

        return "dataHistory/list";
    }

    // Read
    @GetMapping({"", "/"})
    public String dataHistoryRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        DataHistoryDto dataHistoryDto = dataHistoryService.findHistoryByIdx(idx);

        model.addAttribute("dataHistoryDto", dataHistoryDto);

        return "dataHistory/read";
    }
}