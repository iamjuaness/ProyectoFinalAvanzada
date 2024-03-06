package com.avanzada.unilocal.Unilocal.dto;

import com.avanzada.unilocal.Unilocal.entity.Comment;
import com.avanzada.unilocal.Unilocal.enums.BusinessType;

import java.util.List;
public record CreatePlaceDto(
        String description,
        String name,
        List<String> schedules,
        List<String> images,
        List<Comment> comments,
        BusinessType businessType,
        List<String> phones
){}