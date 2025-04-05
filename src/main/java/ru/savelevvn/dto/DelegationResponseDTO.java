package ru.savelevvn.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с информацией о делегировании")
public record DelegationResponseDTO(
        @Schema(description = "ID делегирования", example = "1")
        Long id,

        @Schema(description = "ID делегирующего", example = "2")
        Long delegatorId,

        @Schema(description = "ID делегата", example = "3")
        Long delegateId,

        @Schema(description = "ID привилегии", example = "4")
        Long privilegeId,

        @Schema(description = "Время начала", example = "2023-01-01T10:00:00")
        LocalDateTime startTime,

        @Schema(description = "Время окончания", example = "2023-01-31T23:59:59")
        LocalDateTime endTime,

        @Schema(description = "Активно ли делегирование", example = "true")
        boolean active,

        @Schema(description = "Комментарий", example = "Временное делегирование на время отпуска")
        String comment
) {}