package ru.savelevvn.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String phoneNumber,
        LocalDateTime createdAt,
        boolean enabled,
        boolean twoFactorEnabled,
        Set<String> roles,
        Set<Long> groupIds
) {}