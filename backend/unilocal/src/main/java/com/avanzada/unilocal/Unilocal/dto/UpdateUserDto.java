package com.avanzada.unilocal.Unilocal.dto;

public record UpdateUserDto(

        String name,
        String photo,
        String nickname,
        String email,
        String residenceCity

) {
}
