package ru.savelevvn.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с информацией о привилегии")
public record PrivilegeResponseDTO(
        @Schema(description = "ID привилегии", example = "1")
        Long id,

        @Schema(description = "Название привилегии", example = "EDIT_USERS")
        String name,

        @Schema(description = "Описание привилегии", example = "Позволяет редактировать пользователей")
        String description
) {}