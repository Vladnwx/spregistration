package ru.savelevvn.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record DelegationRequestDTO(
        @NotNull Long delegatorId,
        @NotNull Long delegateId,
        @NotNull Long privilegeId,
        @NotNull LocalDateTime startTime,
        @Future @NotNull LocalDateTime endTime,
        String comment
) {}