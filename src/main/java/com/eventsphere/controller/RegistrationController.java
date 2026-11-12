
package com.eventsphere.controller;

import com.eventsphere.dto.RegistrationRequest;
import com.eventsphere.dto.RegistrationResponse;
import com.eventsphere.model.Event;
import com.eventsphere.model.Registration;
import com.eventsphere.repository.EventRepository;
import com.eventsphere.repository.RegistrationRepository;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;

    public RegistrationController(
            EventRepository eventRepository,
            RegistrationRepository registrationRepository) {

        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    @PostMapping
    public RegistrationResponse register(
            @Valid @RequestBody RegistrationRequest request) {

        Event event = eventRepository.findById(request.eventId())
                .filter(Event::isActive)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Event not found"
                ));

        if (registrationRepository.existsByEventIdAndEmailIgnoreCase(
                event.getId(), request.email())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This email is already registered for this event"
            );
        }

        registrationRepository.save(
                new Registration(
                        request.attendeeName().trim(),
                        request.email().trim().toLowerCase(),
                        event
                )
        );

        String invite = event.getTelegramInviteLink();

        String message = (invite == null || invite.isBlank())
                ? "Registration successful. The organizer has not added a Telegram group yet."
                : "Registration successful. Join the event community using the private invite link.";

        return new RegistrationResponse(message, invite);
    }
}
