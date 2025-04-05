package ru.savelevvn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с токенами аутентификации")
public class AuthResponse {
    @Schema(description = "Access токен", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Refresh токен", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;

    @Schema(description = "Тип токена", example = "Bearer")
    @Builder.Default
    private String tokenType = "Bearer";

    @Schema(description = "Время жизни токена в секундах", example = "3600")
    @Builder.Default
    private long expiresIn = 3600;
}