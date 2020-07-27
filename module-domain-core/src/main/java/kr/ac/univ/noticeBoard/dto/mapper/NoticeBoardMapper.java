package kr.ac.univ.noticeBoard.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.noticeBoard.dto.NoticeBoardDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoticeBoardMapper extends EntityMapper<NoticeBoardDto, NoticeBoard> {
    NoticeBoardMapper INSTANCE = Mappers.getMapper(NoticeBoardMapper.class);

}