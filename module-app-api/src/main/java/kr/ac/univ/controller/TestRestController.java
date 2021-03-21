package kr.ac.univ.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestRestController {
    @PostMapping
    public ResponseEntity<?> postPublication() {

        return new ResponseEntity<>("{1}", HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putPublication() {

        return new ResponseEntity<>("{1}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deletePublication()  {

        return new ResponseEntity<>("{1}", HttpStatus.OK);
    }
}
