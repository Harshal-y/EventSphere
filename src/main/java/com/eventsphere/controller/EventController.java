
package com.eventsphere.controller;

import com.eventsphere.dto.EventResponse;
import com.eventsphere.repository.EventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<EventResponse> getEvents() {
        return eventRepository.findByActiveTrueOrderByEventDateAsc()
                .stream()
                .map(EventResponse::from)
                .toList();
    }
}
