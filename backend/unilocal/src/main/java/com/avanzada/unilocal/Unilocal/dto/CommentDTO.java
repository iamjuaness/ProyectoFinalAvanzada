package com.avanzada.unilocal.Unilocal.dto;

import com.avanzada.unilocal.Unilocal.resources.Qualification;

public record CommentDTO(
        String message,
        Qualification qualification
) {
}
