package ru.savelevvn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на регистрацию пользователя")
public class RegisterRequest {
    @Schema(
            description = "Имя пользователя (3-50 символов, только буквы, цифры и подчеркивания)",
            example = "user123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 50, message = "Имя пользователя должно быть от 3 до 50 символов")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+$",
            message = "Имя пользователя может содержать только буквы, цифры и подчеркивания"
    )
    private String username;

    @Schema(
            description = "Email пользователя (должен быть валидным)",
            example = "user@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть валидным")
    private String email;

    @Schema(
            description = "Пароль (8-100 символов)",
            example = "SecurePassword123!",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, max = 100, message = "Пароль должен быть от 8 до 100 символов")
    private String password;
}