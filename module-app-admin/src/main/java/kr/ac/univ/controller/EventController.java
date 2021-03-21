package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.event.dto.EventDto;
import kr.ac.univ.event.service.EventService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // List
    @GetMapping("/list")
    public String eventList(Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("eventDtoList", eventService.findEventList(pageable, searchDto));

        return "event/list";
    }

    // Form
    @GetMapping("/form{idx}")
    public String eventForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        EventDto eventDto = eventService.findEventByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (eventDto.isAccess()) {
            model.addAttribute("eventDto", eventDto);

            returnPage = "event/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String eventRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        EventDto eventDto = eventService.findEventByIdx(idx);

        model.addAttribute("eventDto", eventDto);

        return "event/read";
    }
}