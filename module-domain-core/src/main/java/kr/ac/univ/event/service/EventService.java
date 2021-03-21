package kr.ac.univ.event.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.event.domain.Event;
import kr.ac.univ.event.dto.EventDto;
import kr.ac.univ.event.dto.mapper.EventMapper;
import kr.ac.univ.event.repository.EventRepository;
import kr.ac.univ.event.repository.EventRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {
    @Value("${module.name}")
    private String moduleName;
    private final EventRepository eventRepository;
    private final EventRepositoryImpl eventRepositoryImpl;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, EventRepositoryImpl eventRepositoryImpl, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.eventRepositoryImpl = eventRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<EventDto> findEventList(Pageable pageable, SearchDto searchDto) {
        Page<Event> eventList = null;
        Page<EventDto> eventDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                if ("module-app-admin".equals(moduleName)) {
                    eventList = eventRepository.findAllByTitleContainingOrderByStartDateDesc(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    eventList = eventRepository.findAllByTitleContainingAndActiveStatusIsOrderByStartDateDesc(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    eventList = null;
                }
                break;
            case "CONTENT":
                if ("module-app-admin".equals(moduleName)) {
                    eventList = eventRepository.findAllByContentContainingOrderByStartDateDesc(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    eventList = eventRepository.findAllByContentContainingAndActiveStatusIsOrderByStartDateDesc(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    eventList = null;
                }
                break;
            case "ID":
                if ("module-app-admin".equals(moduleName)) {
                    eventList = eventRepository.findAllByCreatedByContainingOrderByStartDateDesc(pageable, searchDto.getKeyword());
                } else if ("module-app-web".equals(moduleName)) {
                    eventList = eventRepository.findAllByCreatedByContainingAndActiveStatusIsOrderByStartDateDesc(pageable, searchDto.getKeyword(), ActiveStatus.ACTIVE);
                } else {
                    eventList = null;
                }
                break;
            default:
                if ("module-app-admin".equals(moduleName)) {
                    eventList = eventRepository.findAllByOrderByStartDateDesc(pageable);
                } else if ("module-app-web".equals(moduleName)) {
                    eventList = eventRepository.findAllByActiveStatusIsOrderByStartDateDesc(pageable, ActiveStatus.ACTIVE);
                } else {
                    eventList = null;
                }
                break;
        }

        eventDtoList = new PageImpl<>(EventMapper.INSTANCE.toDto(eventList.getContent()), pageable, eventList.getTotalElements());

        // NewIcon 판별
        for (EventDto eventDto : eventDtoList) {
            eventDto.setNewIcon(NewIconCheck.isNew(eventDto.getCreatedDate()));
        }

        return eventDtoList;
    }

    public List<EventDto> findTop4ByOrderByCurrentDate() {
        return EventMapper.INSTANCE.toDto(eventRepository.findTop4ByStartDateAfterOrEndDateAfterOrderByStartDate(LocalDate.now().minusDays(1), LocalDate.now().minusDays(1)));
    }

    public Long insertEvent(EventDto eventDto) {
        return eventRepository.save(EventMapper.INSTANCE.toEntity(eventDto)).getIdx();
    }

    public EventDto findEventByIdx(Long idx) {
        EventDto eventDto = EventMapper.INSTANCE.toDto(eventRepository.findById(idx).orElse(new Event()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            eventDto.setAccess(true);
        }
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        else {
            eventDto.setAccess(AccessCheck.isAccessInGeneral(eventDto.getCreatedBy(), userRepository.findByUsername(eventDto.getCreatedBy()).getAuthorityType().name()));
        }

        eventRepositoryImpl.updateViewsByIdx(idx);
        eventDto.setViews(eventDto.getViews() + 1);

        return eventDto;
    }

    @Transactional
    public Long updateEvent(Long idx, EventDto eventDto) {
        Event persistEvent = eventRepository.getOne(idx);
        Event event = EventMapper.INSTANCE.toEntity(eventDto);

        persistEvent.update(event);

        return eventRepository.save(persistEvent).getIdx();
    }

    public void deleteEventByIdx(Long idx) {
        eventRepository.deleteById(idx);
    }
}