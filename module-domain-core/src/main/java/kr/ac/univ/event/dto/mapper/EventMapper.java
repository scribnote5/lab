package kr.ac.univ.event.dto.mapper;

import kr.ac.univ.common.dto.mapper.EntityMapper;
import kr.ac.univ.event.domain.Event;
import kr.ac.univ.event.dto.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper extends EntityMapper<EventDto, Event> {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
}