package ru.savelevvn.service;

import ru.savelevvn.dto.AuthRequest;
import ru.savelevvn.dto.AuthResponse;
import ru.savelevvn.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
    AuthResponse refreshToken(String refreshToken);
}