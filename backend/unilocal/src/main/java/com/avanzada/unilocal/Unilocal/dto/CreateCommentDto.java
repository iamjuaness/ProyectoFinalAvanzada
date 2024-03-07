package com.avanzada.unilocal.Unilocal.dto;

import com.avanzada.unilocal.Unilocal.resources.Qualification;
import jakarta.validation.constraints.NotBlank;

public record CreateCommentDto(
        @NotBlank(message = "message is required")
        String message,
        Qualification qualification
) {}