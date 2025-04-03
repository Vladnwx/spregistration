package ru.savelevvn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank String username,
        @Size(min = 8, max = 100) String password,
        @Email @NotBlank String email,
        String phoneNumber,
        boolean enabled,
        boolean twoFactorEnabled
) {}