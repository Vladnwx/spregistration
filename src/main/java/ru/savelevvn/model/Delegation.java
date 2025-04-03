package ru.savelevvn.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delegations")
public class Delegation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delegator_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_delegations_delegator"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User delegator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delegate_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_delegations_delegate"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User delegate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_delegations_privilege"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Privilege privilege;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    @Column(length = 500)
    private String comment;

    // Метод для проверки активной делегации
    public boolean isActive() {
        return active &&
                LocalDateTime.now().isAfter(startTime) &&
                LocalDateTime.now().isBefore(endTime);
    }

    // Метод для проверки корректности временного интервала
    public boolean isTimeRangeValid() {
        return endTime.isAfter(startTime) &&
                startTime.isAfter(LocalDateTime.now().minusYears(1)); // Максимум 1 год вперед
    }
}