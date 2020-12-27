package kr.ac.univ.learnMoreVideo.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.learnMoreVideo.domain.LearnMoreVideo;
import kr.ac.univ.learnMoreVideo.domain.LearnMoreVideoAttachedFile;
import kr.ac.univ.learnMoreVideo.dto.LearnMoreVideoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LearnMoreVideoMapper extends EntityMapper<LearnMoreVideoDto, LearnMoreVideo> {
    LearnMoreVideoMapper INSTANCE = Mappers.getMapper(LearnMoreVideoMapper.class);

    default LearnMoreVideoDto toDto(LearnMoreVideoDto learnMoreDto, List<LearnMoreVideoAttachedFile> attachedFileList) {
        for (LearnMoreVideoAttachedFile attachedFile : attachedFileList) {
            learnMoreDto.getAttachedFileList().add(attachedFile);
        }

        return learnMoreDto;
    }
}