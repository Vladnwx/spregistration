package ru.savelevvn.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с JWT токенами")
public record JwtResponse(
        @Schema(description = "Access токен", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @Schema(description = "Refresh токен", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String refreshToken
) {}