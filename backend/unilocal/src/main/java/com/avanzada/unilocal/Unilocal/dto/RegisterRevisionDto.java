package com.avanzada.unilocal.Unilocal.dto;

import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import jakarta.validation.constraints.NotBlank;

public record RegisterRevisionDto(
        @NotBlank(message = "mod is required")
        String mod,
        @NotBlank(message = "description is required")
        String description
) {
}
