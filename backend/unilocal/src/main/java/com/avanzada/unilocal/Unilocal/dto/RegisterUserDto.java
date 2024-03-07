package com.avanzada.unilocal.Unilocal.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterUserDto(
        @NotBlank(message = "name is required")
        @Length(min = 7, max = 50)
        String name,
        @NotBlank(message = "photo is required")
        String photo,
        @NotBlank(message = "nickname is required")
        String nickname,
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "password is required")
        @Length(min = 8)
        String password,
        @NotBlank(message = "residenceCity is required")
        String residenceCity
) {}
