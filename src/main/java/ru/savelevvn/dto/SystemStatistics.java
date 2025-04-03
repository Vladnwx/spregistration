package ru.savelevvn.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemStatistics {
    private long totalUsers;
    private long activeUsers;
    private long lockedUsers;
    private long totalGroups;
    private long totalRoles;
}