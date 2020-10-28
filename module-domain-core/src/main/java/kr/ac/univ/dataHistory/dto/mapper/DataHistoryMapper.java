package kr.ac.univ.dataHistory.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.dataHistory.domain.DataHistory;
import kr.ac.univ.dataHistory.dto.DataHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DataHistoryMapper extends EntityMapper<DataHistoryDto, DataHistory> {
    DataHistoryMapper INSTANCE = Mappers.getMapper(DataHistoryMapper.class);

}