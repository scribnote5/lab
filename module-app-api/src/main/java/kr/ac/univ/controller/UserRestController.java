package kr.ac.univ.controller;

import kr.ac.univ.user.dto.UserDto;
import kr.ac.univ.user.service.UserAttachedFileService;
import kr.ac.univ.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;
    private final UserAttachedFileService userAttachedFileService;

    public UserRestController(UserService userService, UserAttachedFileService userAttachedFileService) {
        this.userService = userService;
        this.userAttachedFileService = userAttachedFileService;
    }

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody UserDto userDto) {
        // 추후 변경
        if (userService.isDupulicationUserByUsername(userDto.getUsername())) {

        }

        Long idx = userService.joinUser(userDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putUser(@PathVariable("idx") Long idx, @RequestBody UserDto userDto) {
        userService.updateUser(idx, userDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteUser(@PathVariable("idx") Long idx) {
        userService.deleteUserByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @GetMapping("/validation/username/{username}")
    public ResponseEntity<?> checkDuplicateUsername(@PathVariable("username") String username) {

        return new ResponseEntity<>(userService.isDupulicationUserByUsername(username), HttpStatus.CREATED);
    }

    // 첨부 파일 업로드
    @PostMapping("/attachedFile")
    public ResponseEntity<?> uploadAttachedFile(Long idx, String createdBy, MultipartFile[] files) throws Exception {
        userAttachedFileService.uploadAttachedFile(idx, createdBy, files);

        return new ResponseEntity<>("성공!", HttpStatus.CREATED);
    }

    // 첨부 파일 삭제
    @DeleteMapping("/attachedFile/{idx}")
    public ResponseEntity<?> deleteAttachedFile(@PathVariable("idx") Long idx) throws Exception {
        userAttachedFileService.deleteAttachedFile(idx);

        return new ResponseEntity<>("성공!", HttpStatus.OK);
    }
}