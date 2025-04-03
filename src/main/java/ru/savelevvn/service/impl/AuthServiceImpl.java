package ru.savelevvn.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savelevvn.dto.*;
import ru.savelevvn.exception.UserAlreadyExistsException;
import ru.savelevvn.model.User;
import ru.savelevvn.repository.UserRepository;
import ru.savelevvn.service.AuthService;
import ru.savelevvn.service.JwtService;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Проверяем существование пользователя
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Attempt to register existing username: {}", request.getUsername());
            throw new UserAlreadyExistsException("username", request.getUsername());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Attempt to register existing email: {}", request.getEmail());
            throw new UserAlreadyExistsException("email", request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Кодируем пароль
                .enabled(true)
                .build();

        // Сохраняем пользователя
        User savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getId());

        // Генерируем токены
        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        log.debug("Tokens generated for user ID: {}", savedUser.getId());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        log.debug("Authentication attempt for user: {}", request.getUsername());

        try {
            // Аутентифицируем пользователя
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // Получаем пользователя
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            log.debug("User authenticated: {}", user.getUsername());

            // Обновляем данные аутентификации
            user.setLastLogin(LocalDateTime.now());
            user.setFailedLoginAttempts(0);
            user.setLocked(false);
            userRepository.save(user);

            // Генерируем новые токены
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            log.debug("New tokens generated for user: {}", user.getUsername());

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", request.getUsername(), e);
            throw new RuntimeException("Authentication failed");
        }
    }
    @Override
    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        log.debug("Refreshing token");

        // Извлекаем имя пользователя из токена
        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            log.warn("Invalid refresh token - no username");
            throw new RuntimeException("Invalid refresh token");
        }

        // Получаем пользователя
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.debug("User found for token refresh: {}", user.getUsername());

        // Проверяем валидность токена
        if (!jwtService.isTokenValid(refreshToken, user)) {
            log.warn("Invalid refresh token for user: {}", user.getUsername());
            throw new RuntimeException("Invalid refresh token");
        }

        // Генерируем новые токены
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        log.debug("Tokens refreshed for user: {}", user.getUsername());

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}