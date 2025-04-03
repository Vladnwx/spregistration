package ru.savelevvn.dto;

import java.util.Set;

public record GroupResponseDTO(
        Long id,
        String name,
        String description,
        boolean isSystem,
        Set<String> roles,
        Set<Long> userIds
) {}