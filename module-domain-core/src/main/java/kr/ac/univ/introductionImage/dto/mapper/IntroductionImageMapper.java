package kr.ac.univ.introductionImage.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.introductionImage.domain.IntroductionImage;
import kr.ac.univ.introductionImage.domain.IntroductionImageAttachedFile;
import kr.ac.univ.introductionImage.dto.IntroductionImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IntroductionImageMapper extends EntityMapper<IntroductionImageDto, IntroductionImage> {
    IntroductionImageMapper INSTANCE = Mappers.getMapper(IntroductionImageMapper.class);

    default IntroductionImageDto toDto(IntroductionImageDto projectDto, List<IntroductionImageAttachedFile> attachedFileList) {
        for (IntroductionImageAttachedFile attachedFile : attachedFileList) {
            projectDto.getAttachedFileList().add(attachedFile);
        }

        return projectDto;
    }
}