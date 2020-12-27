package kr.ac.univ.learnMoreRead.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.learnMoreRead.domain.LearnMoreRead;
import kr.ac.univ.learnMoreRead.domain.LearnMoreReadAttachedFile;
import kr.ac.univ.learnMoreRead.dto.LearnMoreReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LearnMoreReadMapper extends EntityMapper<LearnMoreReadDto, LearnMoreRead> {
    LearnMoreReadMapper INSTANCE = Mappers.getMapper(LearnMoreReadMapper.class);

    default LearnMoreReadDto toDto(LearnMoreReadDto learnMoreDto, List<LearnMoreReadAttachedFile> attachedFileList) {
        for (LearnMoreReadAttachedFile attachedFile : attachedFileList) {
            learnMoreDto.getAttachedFileList().add(attachedFile);
        }

        return learnMoreDto;
    }
}