package com.avanzada.unilocal.Unilocal.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearTipoDto(
        @NotBlank(message = "El tipo es requerido") String tipo
) {
}
