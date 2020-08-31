package kr.ac.univ.publication.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.publication.domain.Publication;
import kr.ac.univ.publication.domain.PublicationAttachedFile;
import kr.ac.univ.publication.dto.PublicationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublicationMapper extends EntityMapper<PublicationDto, Publication> {
    PublicationMapper INSTANCE = Mappers.getMapper(PublicationMapper.class);

    default PublicationDto toDto(PublicationDto publicationDto, List<PublicationAttachedFile> attachedFileList) {
        for (PublicationAttachedFile attachedFile : attachedFileList) {
            publicationDto.getAttachedFileList().add(attachedFile);
        }

        return publicationDto;
    }
}