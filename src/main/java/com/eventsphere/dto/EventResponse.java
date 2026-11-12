
package com.eventsphere.dto;

import com.eventsphere.model.Event;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String description,
        String category,
        String venue,
        String city,
        LocalDateTime eventDate,
        String imageUrl,
        BigDecimal price) {

    public static EventResponse from(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getCategory(),
                event.getVenue(),
                event.getCity(),
                event.getEventDate(),
                event.getImageUrl(),
                event.getPrice()
        );
    }
}
