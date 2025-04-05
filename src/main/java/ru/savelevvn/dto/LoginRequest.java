package ru.savelevvn.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на вход в систему")
public record LoginRequest(
        @Schema(description = "Имя пользователя", example = "user123")
        @NotBlank String username,

        @Schema(description = "Пароль", example = "password123")
        @NotBlank String password
) {}