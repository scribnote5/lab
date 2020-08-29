package kr.ac.univ.noticeBoard.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.noticeBoard.domain.NoticeBoardComment;
import kr.ac.univ.noticeBoard.dto.NoticeBoardCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoticeBoardCommentMapper extends EntityMapper<NoticeBoardCommentDto, NoticeBoardComment> {
    NoticeBoardCommentMapper INSTANCE = Mappers.getMapper(NoticeBoardCommentMapper.class);
}