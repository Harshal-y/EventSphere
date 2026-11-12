
package com.eventsphere.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrationRequest(
        @NotNull Long eventId,
        @NotBlank String attendeeName,
        @NotBlank @Email String email) {}
