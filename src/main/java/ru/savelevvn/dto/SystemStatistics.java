package ru.savelevvn.dto;

import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@Schema(description = "Статистика системы")
public class SystemStatistics {
    @Schema(description = "Общее количество пользователей", example = "150")
    private long totalUsers;

    @Schema(description = "Количество активных пользователей", example = "120")
    private long activeUsers;

    @Schema(description = "Количество заблокированных пользователей", example = "5")
    private long lockedUsers;

    @Schema(description = "Общее количество групп", example = "10")
    private long totalGroups;

    @Schema(description = "Общее количество ролей", example = "5")
    private long totalRoles;
}