package com.avanzada.unilocal.Unilocal.dto;

import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
        @NotBlank
        String asunto,
        @NotBlank
        String body,
        @NotBlank
        String destinatario
) {
}
