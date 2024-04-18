package com.avanzada.unilocal.global.dto;

public record MensajeAuthDto<T>(
        boolean error,
        T respuesta
) {
}
