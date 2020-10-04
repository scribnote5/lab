package kr.ac.univ.category.dto.mapper;

import kr.ac.univ.category.domain.Category;
import kr.ac.univ.category.dto.CategoryDto;
import kr.ac.univ.common.dto.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDto, Category> {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
}