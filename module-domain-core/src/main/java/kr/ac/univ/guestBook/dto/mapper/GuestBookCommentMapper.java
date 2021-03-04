package kr.ac.univ.guestBook.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.guestBook.domain.GuestBookComment;
import kr.ac.univ.guestBook.dto.GuestBookCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GuestBookCommentMapper extends EntityMapper<GuestBookCommentDto, GuestBookComment> {
    GuestBookCommentMapper INSTANCE = Mappers.getMapper(GuestBookCommentMapper.class);
}