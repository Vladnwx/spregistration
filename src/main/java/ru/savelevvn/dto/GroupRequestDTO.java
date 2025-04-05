package ru.savelevvn.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Запрос на создание/обновление группы")
public record GroupRequestDTO(
        @Schema(description = "Название группы", example = "Менеджеры")
        @NotBlank(message = "Group name cannot be blank")
        String name,

        @Schema(description = "Описание группы", example = "Группа менеджеров проекта")
        String description,

        @Schema(description = "Системная ли группа", example = "false")
        boolean isSystem
) {}