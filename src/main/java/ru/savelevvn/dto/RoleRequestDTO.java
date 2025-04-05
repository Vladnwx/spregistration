package ru.savelevvn.dto;

import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на создание/обновление роли")
public record RoleRequestDTO(
        @Schema(description = "Название роли (должно начинаться с ROLE_ и быть в верхнем регистре)",
                example = "ROLE_ADMIN")
        @Pattern(regexp = "^ROLE_[A-Z_]+$", message = "Role name must start with ROLE_ and be uppercase")
        String name,

        @Schema(description = "Описание роли", example = "Роль администратора системы")
        String description
) {}