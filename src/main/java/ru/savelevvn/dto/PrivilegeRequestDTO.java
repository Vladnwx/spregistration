package ru.savelevvn.dto;

import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на создание/обновление привилегии")
public record PrivilegeRequestDTO(
        @Schema(description = "Название привилегии (только заглавные буквы и подчеркивания)", example = "EDIT_USERS")
        @Pattern(regexp = "^[A-Z_]+$", message = "Privilege name must be uppercase with underscores")
        String name,

        @Schema(description = "Описание привилегии", example = "Позволяет редактировать пользователей")
        String description
) {}