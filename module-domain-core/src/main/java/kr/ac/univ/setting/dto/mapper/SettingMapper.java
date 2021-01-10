package kr.ac.univ.setting.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.setting.domain.Setting;
import kr.ac.univ.setting.dto.SettingDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SettingMapper extends EntityMapper<SettingDto, Setting> {
    SettingMapper INSTANCE = Mappers.getMapper(SettingMapper.class);
}