package ru.savelevvn.dto;

import java.time.LocalDateTime;

public record DelegationResponseDTO(
        Long id,
        Long delegatorId,
        Long delegateId,
        Long privilegeId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean active,
        String comment
) {}