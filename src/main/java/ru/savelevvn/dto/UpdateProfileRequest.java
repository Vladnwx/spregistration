package ru.savelevvn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Запрос на обновление профиля пользователя")
public class UpdateProfileRequest {
    @Schema(description = "Имя пользователя (3-50 символов)", example = "new_username")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers and underscores")
    private String username;

    @Schema(description = "Email пользователя", example = "new_email@example.com")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Номер телефона", example = "+79123456789")
    @Size(max = 20, message = "Phone number must be less than 20 characters")
    private String phoneNumber;
}