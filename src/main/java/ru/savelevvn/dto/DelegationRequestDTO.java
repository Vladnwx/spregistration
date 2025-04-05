package ru.savelevvn.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на делегирование привилегии")
public record DelegationRequestDTO(
        @Schema(description = "ID делегирующего", example = "1")
        @NotNull Long delegatorId,

        @Schema(description = "ID делегата", example = "2")
        @NotNull Long delegateId,

        @Schema(description = "ID привилегии", example = "3")
        @NotNull Long privilegeId,

        @Schema(description = "Время начала", example = "2023-01-01T10:00:00")
        @NotNull LocalDateTime startTime,

        @Schema(description = "Время окончания (должно быть в будущем)", example = "2023-01-31T23:59:59")
        @Future @NotNull LocalDateTime endTime,

        @Schema(description = "Комментарий", example = "Делегирование на время отпуска")
        String comment
) {}