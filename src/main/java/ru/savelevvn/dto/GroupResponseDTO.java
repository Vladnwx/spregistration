package ru.savelevvn.dto;

import java.util.Set;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с информацией о группе")
public record GroupResponseDTO(
        @Schema(description = "ID группы", example = "1")
        Long id,

        @Schema(description = "Название группы", example = "Администраторы")
        String name,

        @Schema(description = "Описание группы", example = "Группа администраторов системы")
        String description,

        @Schema(description = "Системная ли группа", example = "true")
        boolean isSystem,

        @Schema(description = "Роли группы", example = "[\"ADMIN\", \"MANAGER\"]")
        Set<String> roles,

        @Schema(description = "ID пользователей в группе", example = "[1, 2, 3]")
        Set<Long> userIds
) {}