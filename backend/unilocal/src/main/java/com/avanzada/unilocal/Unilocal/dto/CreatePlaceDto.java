package com.avanzada.unilocal.Unilocal.dto;

import com.avanzada.unilocal.Unilocal.entity.Comment;
import com.avanzada.unilocal.Unilocal.enums.BusinessType;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;
public record CreatePlaceDto(
        @NotBlank(message = "Description is required")
        @Length(max = 150)
        String description,
        @NotBlank(message = "Name is required")
        @Length(max = 50, min = 6)
        String name,
        @NotBlank(message = "The schedules are required")
        List<String> schedules,
        @NotBlank(message = "Images are required")
        List<String> images,
        @NotBlank(message = "businessType is required")
        BusinessType businessType,
        @NotBlank(message = "phones sra required")
        List<String> phones
){}