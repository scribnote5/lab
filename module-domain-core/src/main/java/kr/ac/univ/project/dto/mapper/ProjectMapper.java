package kr.ac.univ.project.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.project.domain.Project;
import kr.ac.univ.project.domain.ProjectAttachedFile;
import kr.ac.univ.project.dto.ProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDto, Project> {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    default ProjectDto toDto(ProjectDto projectDto, List<ProjectAttachedFile> attachedFileList) {
        for (ProjectAttachedFile attachedFile : attachedFileList) {
            projectDto.getAttachedFileList().add(attachedFile);
        }

        return projectDto;
    }
}