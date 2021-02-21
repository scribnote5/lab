package kr.ac.univ.controller;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.album.dto.AlbumDto;
import kr.ac.univ.album.service.AlbumAttachedFileService;
import kr.ac.univ.album.service.AlbumService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/album")
public class AlbumController {
    private final AlbumService albumService;
    private final AlbumAttachedFileService albumAttachedFileService;

    public AlbumController(AlbumService albumService, AlbumAttachedFileService albumAttachedFileService) {
        this.albumService = albumService;
        this.albumAttachedFileService = albumAttachedFileService;
    }

    // List
    @GetMapping("/list")
    public String albumList(Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("albumDtoList", albumService.findAlbumList(pageable, searchDto));

        return "album/list";
    }

    // Form
    @GetMapping("/form{idx}")
    public String albumForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        AlbumDto albumDto = albumService.findAlbumByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (albumDto.isAccess()) {
            albumDto = albumAttachedFileService.findAttachedFileByAlbumIdx(idx, albumDto);

            model.addAttribute("albumDto", albumDto);
            model.addAttribute("albumDtoList", albumService.findAllByActiveStatusIsAndMainPagePriorityGreaterThanEqualOrderByMainPagePriorityAsc());

            returnPage = "album/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String albumRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        AlbumDto albumDto = null;

        albumDto = albumService.findAlbumByIdx(idx);
        albumDto = albumAttachedFileService.findAttachedFileByAlbumIdx(idx, albumDto);

        model.addAttribute("albumDto", albumDto);

        return "album/read";
    }
}