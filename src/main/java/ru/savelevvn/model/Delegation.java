package ru.savelevvn.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delegations")
@Schema(description = "Сущность делегирования прав между пользователями")
public class Delegation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор делегирования", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delegator_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_delegations_delegator"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Пользователь, который делегирует права", required = true)
    private User delegator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delegate_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_delegations_delegate"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Пользователь, которому делегируются права", required = true)
    private User delegate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_delegations_privilege"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Привилегия, которая делегируется", required = true)
    private Privilege privilege;

    @Column(name = "start_time", nullable = false)
    @Schema(
            description = "Дата и время начала действия делегирования",
            example = "2023-01-01T09:00:00",
            required = true
    )
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    @Schema(
            description = "Дата и время окончания действия делегирования",
            example = "2023-01-07T18:00:00",
            required = true
    )
    private LocalDateTime endTime;

    @Builder.Default
    @Column(nullable = false)
    @Schema(
            description = "Флаг активности делегирования (может быть отключено вручную)",
            example = "true"
    )
    private boolean active = true;

    @Column(length = 500)
    @Schema(description = "Комментарий к делегированию", example = "Делегирование прав на время отпуска")
    private String comment;

    // Метод для проверки активной делегации
    @Schema(
            description = "Проверяет, активно ли делегирование в текущий момент времени",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    public boolean isActive() {
        return active &&
                LocalDateTime.now().isAfter(startTime) &&
                LocalDateTime.now().isBefore(endTime);
    }

    // Метод для проверки корректности временного интервала
    @Schema(
            description = "Проверяет корректность временного интервала делегирования",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    public boolean isTimeRangeValid() {
        return endTime.isAfter(startTime) &&
                startTime.isAfter(LocalDateTime.now().minusYears(1)); // Максимум 1 год вперед
    }

    @PrePersist
    @PreUpdate
    private void validateTimeRange() {
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }

    @Version
    private Long version;
}