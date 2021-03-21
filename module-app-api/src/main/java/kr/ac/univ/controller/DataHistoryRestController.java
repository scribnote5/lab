package kr.ac.univ.controller;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.dataHistory.service.DataHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data-historys")
public class DataHistoryRestController {
    private final DataHistoryService dataHistoryService;

    public DataHistoryRestController(DataHistoryService dataHistoryService) {
        this.dataHistoryService = dataHistoryService;
    }

    @PutMapping("/{idx}/{activeStatus}")
    public ResponseEntity<?> putDataHistory(@PathVariable("idx") Long idx, @PathVariable("activeStatus") ActiveStatus activeStatus) {
        dataHistoryService.updateDataHistory(idx, activeStatus);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteDataHistory(@PathVariable("idx") Long idx) {
        dataHistoryService.deleteDataHistoryByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}