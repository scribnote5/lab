package kr.ac.univ.user.dto.mapper;

import java.util.List;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.domain.UserAttachedFile;
import kr.ac.univ.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default UserDto toDto(UserDto userDto, List<UserAttachedFile> attachedFileList) {
        for (UserAttachedFile attachedFile : attachedFileList) {
            userDto.getAttachedFileList().add(attachedFile);
        }

        return userDto;
    }
}