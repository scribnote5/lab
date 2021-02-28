package kr.ac.univ.setting.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.setting.dto.SettingDto;
import kr.ac.univ.setting.domain.Setting;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SettingMapper extends EntityMapper<SettingDto, Setting> {
    SettingMapper INSTANCE = Mappers.getMapper(SettingMapper.class);
}