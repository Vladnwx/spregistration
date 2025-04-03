package ru.savelevvn.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savelevvn.dto.AuthRequest;
import ru.savelevvn.dto.AuthResponse;
import ru.savelevvn.dto.RegisterRequest;
import ru.savelevvn.dto.UserRequestDTO;
import ru.savelevvn.exception.UserAlreadyExistsException;
import ru.savelevvn.model.User;
import ru.savelevvn.service.AuthService;
import ru.savelevvn.service.JwtService;
import ru.savelevvn.service.UserService;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Проверяем существование пользователя
        if (userService.existsByUsername(request.getUsername())) {
            log.info("Registering new user: {}", request.getUsername());
            throw new UserAlreadyExistsException("username", request.getUsername());
        }

        if (userService.existsByEmail(request.getEmail())) {
            log.info("Registering new user: {}", request.getEmail());
            throw new UserAlreadyExistsException("email", request.getEmail());
        }

        // Создаем DTO для UserService
        UserRequestDTO userDTO = new UserRequestDTO(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                null, // phoneNumber
                true, // enabled
                false // twoFactorEnabled
        );

        // Создаем пользователя
        userService.createUser(userDTO);

        // Получаем созданного пользователя
        User user = userService.findByEmail(request.getEmail());

        // Генерируем токены
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        // Аутентифицируем пользователя
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Получаем пользователя
        User user = userService.findByUsername(request.getUsername());

        // Обновляем данные аутентификации
        userService.updateAuthenticationData(
                user.getId(),
                LocalDateTime.now(), // lastLogin
                0, // failedAttempts
                false // locked
        );

        // Генерируем новые токены
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        // Извлекаем имя пользователя из токена
        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        // Получаем пользователя
        User user = userService.findByUsername(username);

        // Проверяем валидность токена
        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // Генерируем новые токены
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}