package com.avanzada.unilocal.Unilocal.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearCiudadDto(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre
) {
}
