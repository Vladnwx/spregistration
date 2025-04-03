package ru.savelevvn.dto;

import jakarta.validation.constraints.Pattern;

public record PrivilegeRequestDTO(
        @Pattern(regexp = "^[A-Z_]+$", message = "Privilege name must be uppercase with underscores")
        String name,
        String description
) {}