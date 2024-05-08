package com.avanzada.unilocal.Unilocal.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateUserDto<T>(
        @NotBlank(message = "name is required")
        @Length(min = 7, max = 50)
        String name,
        @NotBlank(message = "photo is required")
        T photo,
        @NotBlank(message = "nickname is required")
        String nickname,
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "residenceCity is required")
        String residenceCity

) {}
