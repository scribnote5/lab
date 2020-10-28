package kr.ac.univ.loginHistory.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.loginHistory.domain.LoginHistory;
import kr.ac.univ.loginHistory.dto.LoginHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoginHistoryMapper extends EntityMapper<LoginHistoryDto, LoginHistory> {
    LoginHistoryMapper INSTANCE = Mappers.getMapper(LoginHistoryMapper.class);

}