package ru.savelevvn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;

    // Можно добавить дополнительные поля, например:
    @Builder.Default
    private String tokenType = "Bearer";

    @Builder.Default
    private long expiresIn = 3600; // Время жизни access token в секундах
}