package kr.ac.univ.maintenance.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.maintenance.domain.MaintenanceComment;
import kr.ac.univ.maintenance.dto.MaintenanceCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MaintenanceCommentMapper extends EntityMapper<MaintenanceCommentDto, MaintenanceComment> {
    MaintenanceCommentMapper INSTANCE = Mappers.getMapper(MaintenanceCommentMapper.class);
}