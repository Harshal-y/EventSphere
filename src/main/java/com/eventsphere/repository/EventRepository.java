
package com.eventsphere.repository;

import com.eventsphere.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByActiveTrueOrderByEventDateAsc();
}
