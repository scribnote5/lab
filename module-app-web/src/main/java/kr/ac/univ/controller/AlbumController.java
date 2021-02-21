package kr.ac.univ.controller;

import kr.ac.univ.album.dto.AlbumDto;
import kr.ac.univ.album.service.AlbumAttachedFileService;
import kr.ac.univ.album.service.AlbumService;
import kr.ac.univ.common.dto.SearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        List<AlbumDto> albumDtoList = albumService.findAllByActiveStatusIsOrderByMainHashTagDescIdxDesc();
        for(AlbumDto albumDto: albumDtoList) {
            albumAttachedFileService.findAttachedFileByAlbumIdx(albumDto.getIdx(), albumDto);
        }

        model.addAttribute("albumDtoList", albumDtoList);

        return "album/list";
    }
}