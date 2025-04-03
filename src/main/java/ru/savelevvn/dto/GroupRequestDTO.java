package ru.savelevvn.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record GroupRequestDTO(
        @NotBlank(message = "Group name cannot be blank")
        String name,
        String description,
        boolean isSystem
) {}