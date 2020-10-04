package kr.ac.univ.seminar.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.researchField.domain.ResearchField;
import kr.ac.univ.seminar.domain.Seminar;
import kr.ac.univ.seminar.domain.SeminarAttachedFile;
import kr.ac.univ.seminar.dto.SeminarDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeminarMapper extends EntityMapper<SeminarDto, Seminar> {
    SeminarMapper INSTANCE = Mappers.getMapper(SeminarMapper.class);

    default SeminarDto toDto(SeminarDto seminarDto, List<SeminarAttachedFile> attachedFileList) {
        for (SeminarAttachedFile attachedFile : attachedFileList) {
            seminarDto.getAttachedFileList().add(attachedFile);
        }

        return seminarDto;
    }
}