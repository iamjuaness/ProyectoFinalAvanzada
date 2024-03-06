package com.avanzada.unilocal.Unilocal.dto;

public record RegisterUserDto(
        String name,
        String photo,
        String nickname,
        String email,
        String password,
        String residenceCity
) {

}
