package kr.ac.univ.email.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.email.domain.Email;
import kr.ac.univ.email.dto.EmailDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmailMapper extends EntityMapper<EmailDto, Email> {
    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);
}