package kr.ac.univ.maintenance.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.maintenance.domain.Maintenance;
import kr.ac.univ.maintenance.domain.MaintenanceAttachedFile;
import kr.ac.univ.maintenance.dto.MaintenanceDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper extends EntityMapper<MaintenanceDto, Maintenance> {
    MaintenanceMapper INSTANCE = Mappers.getMapper(MaintenanceMapper.class);

    default MaintenanceDto toDto(MaintenanceDto maintenanceDto, List<MaintenanceAttachedFile> attachedFileList) {
        for (MaintenanceAttachedFile attachedFile : attachedFileList) {
            maintenanceDto.getAttachedFileList().add(attachedFile);
        }

        return maintenanceDto;
    }
}