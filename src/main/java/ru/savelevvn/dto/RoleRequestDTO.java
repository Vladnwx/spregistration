package ru.savelevvn.dto;

import jakarta.validation.constraints.Pattern;

public record RoleRequestDTO(
        @Pattern(regexp = "^ROLE_[A-Z_]+$", message = "Role name must start with ROLE_ and be uppercase")
        String name,
        String description
) {}