package kr.ac.univ.aboutUs.dto.mapper;

import kr.ac.univ.aboutUs.domain.AboutUs;
import kr.ac.univ.aboutUs.dto.AboutUsDto;
import kr.ac.univ.common.dto.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AboutUsMapper extends EntityMapper<AboutUsDto, AboutUs> {
    AboutUsMapper INSTANCE = Mappers.getMapper(AboutUsMapper.class);
}