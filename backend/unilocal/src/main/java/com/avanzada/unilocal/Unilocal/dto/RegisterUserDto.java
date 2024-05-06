package com.avanzada.unilocal.Unilocal.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterUserDto(

        @NotBlank(message = "El numero de cedula es obligatorio")
        String cedula,
        @NotBlank(message = "El nombre es obligatorio")
        @Length(min = 7, max = 50)
        String name,
        String photo,
        @NotBlank(message = "El nombre de usuario es obligatorio")
        String nickname,
        @NotBlank(message = "El correo es obligatorio")
        String email,
        @NotBlank(message = "La contrasena es obligatoria")
        @Length(min = 8)
        String password,
        @NotBlank(message = "La contrasena es obligatoria")
        @Length(min = 8)
        String confirmPassword,
        @NotBlank(message = "La ciudad de residencia es obligatoria")
        String residenceCity
) {}
