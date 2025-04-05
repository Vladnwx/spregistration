package ru.savelevvn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Запрос на изменение пароля")
public class ChangePasswordRequest {
    @Schema(description = "Текущий пароль", example = "oldPassword123")
    @NotBlank(message = "Current password cannot be blank")
    private String oldPassword;

    @Schema(description = "Новый пароль", example = "newSecurePassword456")
    @NotBlank(message = "New password cannot be blank")
    @Size(min = 8, message = "New password must be at least 8 characters long")
    private String newPassword;
}