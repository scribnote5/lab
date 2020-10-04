package kr.ac.univ.alumniAssociation.dto.mapper;

import kr.ac.univ.alumniAssociation.domain.AlumniAssociation;
import kr.ac.univ.alumniAssociation.dto.AlumniAssociationDto;
import kr.ac.univ.common.dto.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlumniAssociationMapper extends EntityMapper<AlumniAssociationDto, AlumniAssociation> {
    AlumniAssociationMapper INSTANCE = Mappers.getMapper(AlumniAssociationMapper.class);
}