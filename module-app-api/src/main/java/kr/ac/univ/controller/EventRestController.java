package kr.ac.univ.controller;

import kr.ac.univ.event.dto.EventDto;
import kr.ac.univ.event.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventRestController {
    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<?> postEvent(@RequestBody @Valid EventDto eventDto) {
        Long idx = eventService.insertEvent(eventDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putEvent(@PathVariable("idx") Long idx, @RequestBody @Valid EventDto eventDto) {
        eventService.updateEvent(idx, eventDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteEvent(@PathVariable("idx") Long idx) {
        eventService.deleteEventByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}