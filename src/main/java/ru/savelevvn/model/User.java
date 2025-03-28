package ru.savelevvn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users",
        indexes = {@Index(name = "idx_user_email", columnList = "email"),
                @Index(name = "idx_user_username", columnList = "username")})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false, length = 100)
    private String email;


    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked  = false; // Активность учетной записи (по умолчанию false)

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired = true;

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired = true;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.ROLE_USER;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_privileges",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "privilege")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<UserPrivilege> additionalPrivileges = new HashSet<>();

    public boolean hasPrivilege(UserPrivilege privilege) {
        return role.hasPrivilege(privilege) || additionalPrivileges.contains(privilege);
    }

}
