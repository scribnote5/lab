package kr.ac.univ.researchField.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.researchField.domain.ResearchField;
import kr.ac.univ.researchField.dto.ResearchFieldDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResearchFieldMapper extends EntityMapper<ResearchFieldDto, ResearchField> {
    ResearchFieldMapper INSTANCE = Mappers.getMapper(ResearchFieldMapper.class);

}