package com.avanzada.unilocal.Unilocal.dto;

public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {}
