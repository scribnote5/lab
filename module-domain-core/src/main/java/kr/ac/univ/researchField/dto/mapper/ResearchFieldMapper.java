package kr.ac.univ.researchField.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.researchField.domain.ResearchFieldAttachedFile;
import kr.ac.univ.researchField.dto.ResearchFieldDto;
import kr.ac.univ.researchField.domain.ResearchField;
import kr.ac.univ.researchField.dto.ResearchFieldDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResearchFieldMapper extends EntityMapper<ResearchFieldDto, ResearchField> {
    ResearchFieldMapper INSTANCE = Mappers.getMapper(ResearchFieldMapper.class);

    default ResearchFieldDto toDto(ResearchFieldDto researchFieldDto, List<ResearchFieldAttachedFile> attachedFileList) {
        for (ResearchFieldAttachedFile attachedFile : attachedFileList) {
            researchFieldDto.getAttachedFileList().add(attachedFile);
        }

        return researchFieldDto;
    }
}