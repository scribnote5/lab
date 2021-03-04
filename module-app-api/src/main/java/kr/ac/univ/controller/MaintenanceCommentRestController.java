package kr.ac.univ.controller;

import kr.ac.univ.maintenance.dto.MaintenanceCommentDto;
import kr.ac.univ.maintenance.service.MaintenanceCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/maintenances-comments")
public class MaintenanceCommentRestController {
    private final MaintenanceCommentService maintenanceCommentService;

    public MaintenanceCommentRestController(MaintenanceCommentService maintenanceCommentService) {
        this.maintenanceCommentService = maintenanceCommentService;
    }

    @PostMapping
    public ResponseEntity<?> postMaintenance(@RequestBody @Valid MaintenanceCommentDto maintenanceCommentDto) {
        Long idx = maintenanceCommentService.insertMaintenanceComment(maintenanceCommentDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putMaintenance(@PathVariable("idx") Long idx, @RequestBody MaintenanceCommentDto maintenanceCommentDto) {
        maintenanceCommentService.updateMaintenanceComment(idx, maintenanceCommentDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteMaintenance(@PathVariable("idx") Long idx) {
        maintenanceCommentService.deleteMaintenanceCommentByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}