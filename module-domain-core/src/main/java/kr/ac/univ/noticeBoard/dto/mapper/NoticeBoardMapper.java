package kr.ac.univ.noticeBoard.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.noticeBoard.domain.NoticeBoardAttachedFile;
import kr.ac.univ.noticeBoard.dto.NoticeBoardDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoticeBoardMapper extends EntityMapper<NoticeBoardDto, NoticeBoard> {
    NoticeBoardMapper INSTANCE = Mappers.getMapper(NoticeBoardMapper.class);

    default NoticeBoardDto toDto(NoticeBoardDto noticeBoardDto, List<NoticeBoardAttachedFile> attachedFileList) {
        for (NoticeBoardAttachedFile attachedFile : attachedFileList) {
            noticeBoardDto.getAttachedFileList().add(attachedFile);
        }

        return noticeBoardDto;
    }
}