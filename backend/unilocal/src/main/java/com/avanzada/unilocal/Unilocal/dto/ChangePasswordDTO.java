package com.avanzada.unilocal.Unilocal.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ChangePasswordDTO(
        @NotBlank(message = "id is required")
        int id,
        @NotBlank(message = "password is required")
        @Length(min = 8)
        String password
) {
}
