package ru.savelevvn.dto;

import ru.savelevvn.model.Privilege;
import ru.savelevvn.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.stream.Collectors;

@Schema(description = "Ответ с информацией о роли")
public record RoleResponseDTO(
        @Schema(description = "ID роли", example = "1")
        Long id,

        @Schema(description = "Название роли", example = "ROLE_ADMIN")
        String name,

        @Schema(description = "Описание роли", example = "Роль администратора системы")
        String description,

        @Schema(description = "Список привилегий роли", example = "[\"EDIT_USERS\", \"VIEW_REPORTS\"]")
        Set<String> privileges
) {
    public static RoleResponseDTO fromEntity(Role role) {
        return new RoleResponseDTO(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getPrivileges().stream()
                        .map(Privilege::getName)
                        .collect(Collectors.toSet())
        );
    }
}