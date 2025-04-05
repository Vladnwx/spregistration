package ru.savelevvn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на создание/обновление пользователя")
public record UserRequestDTO(
        @Schema(description = "Имя пользователя (3-50 символов)", example = "user123", required = true)
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers and underscores")
        String username,

        @Schema(description = "Пароль (8-100 символов)", example = "securePassword123", required = true)
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,

        @Schema(description = "Email пользователя", example = "user@example.com", required = true)
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,

        @Schema(description = "Номер телефона", example = "+79123456789")
        @Pattern(regexp = "^\\+?[0-9\\s-]{10,20}$", message = "Phone number is invalid")
        String phoneNumber,

        @Schema(description = "Активен ли пользователь", example = "true")
        boolean enabled,

        @Schema(description = "Включена ли двухфакторная аутентификация", example = "false")
        boolean twoFactorEnabled
) {}