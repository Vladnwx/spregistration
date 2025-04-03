package ru.savelevvn.dto;

import ru.savelevvn.model.Privilege;
import ru.savelevvn.model.Role;

import java.util.Set;
import java.util.stream.Collectors;

public record RoleResponseDTO(
        Long id,
        String name,
        String description,
        Set<String> privileges
) {
    // Статический метод для удобного маппинга из Entity
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