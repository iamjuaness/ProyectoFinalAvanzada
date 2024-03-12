package com.avanzada.unilocal.Unilocal.dto;

import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import jakarta.validation.constraints.NotBlank;

public record RegisterRevisionDto(
        @NotBlank(message = "stateBusiness is required")
        StateUnilocal stateBusiness,
        @NotBlank(message = "mod is required")
        Person mod,
        @NotBlank(message = "description is required")
        String description
) {
}
