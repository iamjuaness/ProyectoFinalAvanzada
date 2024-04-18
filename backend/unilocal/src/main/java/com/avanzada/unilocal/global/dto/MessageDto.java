package com.avanzada.unilocal.global.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


public record MessageDto(
        HttpStatus status,
        String message
) {}
