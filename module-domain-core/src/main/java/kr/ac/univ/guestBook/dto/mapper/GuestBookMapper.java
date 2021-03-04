package kr.ac.univ.guestBook.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.guestBook.domain.GuestBook;
import kr.ac.univ.guestBook.domain.GuestBookAttachedFile;
import kr.ac.univ.guestBook.dto.GuestBookDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestBookMapper extends EntityMapper<GuestBookDto, GuestBook> {
    GuestBookMapper INSTANCE = Mappers.getMapper(GuestBookMapper.class);

    default GuestBookDto toDto(GuestBookDto guestBookDto, List<GuestBookAttachedFile> attachedFileList) {
        for (GuestBookAttachedFile attachedFile : attachedFileList) {
            guestBookDto.getAttachedFileList().add(attachedFile);
        }

        return guestBookDto;
    }
}