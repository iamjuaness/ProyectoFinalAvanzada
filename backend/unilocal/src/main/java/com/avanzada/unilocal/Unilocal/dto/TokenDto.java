package com.avanzada.unilocal.Unilocal.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenDto(
        @NotBlank String token
) {
}
