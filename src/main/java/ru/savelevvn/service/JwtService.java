package ru.savelevvn.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUsername(String token);
    String generateAccessToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateEmailVerificationToken(UserDetails userDetails);

    String generatePasswordResetToken(UserDetails userDetails);
}