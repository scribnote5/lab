package kr.ac.univ.learnMore.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.learnMore.domain.LearnMore;
import kr.ac.univ.learnMore.domain.LearnMoreAttachedFile;
import kr.ac.univ.learnMore.dto.LearnMoreDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LearnMoreMapper extends EntityMapper<LearnMoreDto, LearnMore> {
    LearnMoreMapper INSTANCE = Mappers.getMapper(LearnMoreMapper.class);

    default LearnMoreDto toDto(LearnMoreDto learnMoreDto, List<LearnMoreAttachedFile> attachedFileList) {
        for (LearnMoreAttachedFile attachedFile : attachedFileList) {
            learnMoreDto.getAttachedFileList().add(attachedFile);
        }

        return learnMoreDto;
    }
}