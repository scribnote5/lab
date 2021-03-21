package kr.ac.univ.controller;

import kr.ac.univ.guestBook.dto.GuestBookCommentDto;
import kr.ac.univ.guestBook.service.GuestBookCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/guest-books-comments")
public class GuestBookCommentRestController {
    private final GuestBookCommentService guestBookCommentService;

    public GuestBookCommentRestController(GuestBookCommentService guestBookCommentService) {
        this.guestBookCommentService = guestBookCommentService;
    }

    @PostMapping
    public ResponseEntity<?> postGuestBook(@RequestBody @Valid GuestBookCommentDto guestBookCommentDto) {
        Long idx = guestBookCommentService.insertGuestBookComment(guestBookCommentDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putGuestBook(@PathVariable("idx") Long idx, @RequestBody @Valid GuestBookCommentDto guestBookCommentDto) {
        guestBookCommentService.updateGuestBookComment(idx, guestBookCommentDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteGuestBook(@PathVariable("idx") Long idx) {
        guestBookCommentService.deleteGuestBookCommentByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}