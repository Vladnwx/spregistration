package ru.savelevvn.dto;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {}
