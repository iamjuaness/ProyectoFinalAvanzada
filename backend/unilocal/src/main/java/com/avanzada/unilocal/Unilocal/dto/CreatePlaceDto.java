package com.avanzada.unilocal.Unilocal.dto;

import com.avanzada.unilocal.Unilocal.entity.Tipo;
import com.avanzada.unilocal.Unilocal.enums.BusinessType;
import com.avanzada.unilocal.Unilocal.resources.Horario;
import com.avanzada.unilocal.Unilocal.resources.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CreatePlaceDto<T>(
        @NotBlank(message = "Description is required") @Length(max = 150) String description,
        @NotBlank(message = "Name is required") @Length(max = 50, min = 6) String name,
        @NotEmpty(message = "The schedules are required") List<Horario> schedules,
        @NotEmpty(message = "Images are required") List<T> images,
        @NotNull(message = "businessType is required") String businessType,
        @NotBlank(message = "owner is required") String owner,
        @NotNull(message = "location is required") Location location,
        @NotEmpty(message = "phones are required") List<T> phones
) {
}
