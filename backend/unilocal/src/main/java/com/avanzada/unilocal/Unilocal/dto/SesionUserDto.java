package com.avanzada.unilocal.Unilocal.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record SesionUserDto(
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "password is required")
        @Length(min = 8)
        String password
) {}
