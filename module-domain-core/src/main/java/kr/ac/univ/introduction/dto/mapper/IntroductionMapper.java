package kr.ac.univ.introduction.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.introduction.domain.Introduction;
import kr.ac.univ.introduction.dto.IntroductionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IntroductionMapper extends EntityMapper<IntroductionDto, Introduction> {
    IntroductionMapper INSTANCE = Mappers.getMapper(IntroductionMapper.class);
}