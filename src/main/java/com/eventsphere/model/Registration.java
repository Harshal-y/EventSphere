
package com.eventsphere.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "registrations",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"event_id", "email"}
    )
)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String attendeeName;

    private String email;

    private LocalDateTime registeredAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public Registration() {
    }

    public Registration(String attendeeName, String email, Event event) {

        this.attendeeName = attendeeName;
        this.email = email;
        this.event = event;
        this.registeredAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public Event getEvent() {
        return event;
    }
}
