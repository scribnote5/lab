package kr.ac.univ.album.dto.mapper;

import kr.ac.univ.album.domain.Album;
import kr.ac.univ.album.domain.AlbumAttachedFile;
import kr.ac.univ.album.dto.AlbumDto;
import kr.ac.univ.common.dto.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlbumMapper extends EntityMapper<AlbumDto, Album> {
    AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);

    default AlbumDto toDto(AlbumDto projectDto, List<AlbumAttachedFile> attachedFileList) {
        for (AlbumAttachedFile attachedFile : attachedFileList) {
            projectDto.getAttachedFileList().add(attachedFile);
        }

        return projectDto;
    }
}