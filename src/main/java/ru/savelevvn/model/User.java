package ru.savelevvn.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import ru.savelevvn.model.UserRole;
import ru.savelevvn.model.UserPrivilege;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // Активность учетной записи (по умолчанию true)

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // Дата создания учетной записи

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // Дата обновления учетной записи

    @Column(name = "password_updated_at")
    private LocalDateTime passwordUpdatedAt; // Дата обновления пароля

    @Column(name = "account_expires_at")
    private LocalDateTime accountExpiresAt; // Дата окончания действия учетной записи

    @Column(name = "last_failed_login")
    private LocalDateTime lastFailedLogin; // Дата последнего неудачного входа

    @Column(name = "failed_login_attempts", nullable = false)
    private int failedLoginAttempts = 0; // Количество неудачных попыток входа

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Set<UserRole> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.ROLE_USER; // Роль по умолчанию

}
