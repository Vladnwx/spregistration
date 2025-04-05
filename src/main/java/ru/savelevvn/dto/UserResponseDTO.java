package ru.savelevvn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с информацией о пользователе")
public class UserResponseDTO {
    @Schema(description = "ID пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "user123")
    private String username;

    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    @Schema(description = "Номер телефона", example = "+79123456789")
    private String phoneNumber;

    @Schema(description = "Дата создания", example = "2023-01-01T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Активен ли пользователь", example = "true")
    private boolean enabled;

    @Schema(description = "Включена ли двухфакторная аутентификация", example = "false")
    private boolean twoFactorEnabled;

    @Schema(description = "Роли пользователя", example = "[\"ROLE_ADMIN\", \"ROLE_USER\"]")
    private Set<String> roles;

    @Schema(description = "ID групп пользователя", example = "[1, 2]")
    private Set<Long> groups;
}