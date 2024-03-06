package com.avanzada.unilocal.Unilocal.dto;

import com.avanzada.unilocal.Unilocal.resources.Qualification;

public record CreateCommentDto(
        String message,
        Qualification qualification
) {
}