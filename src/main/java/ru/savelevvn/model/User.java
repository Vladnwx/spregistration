package ru.savelevvn.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_username", columnList = "username")
})
@SQLDelete(sql = "UPDATE users SET enabled = false WHERE id = ?")
@Schema(description = "Сущность пользователя системы")
public class User implements UserDetails {
    // Константа для максимального количества неудачных попыток входа
    public static final int MAX_FAILED_ATTEMPTS = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    @Schema(description = "Уникальное имя пользователя", example = "john_doe", required = true)
    private String username;

    @Size(min = 8, max = 100)
    @Column(nullable = false)
    @Schema(description = "Пароль пользователя (мин. 8 символов)", example = "securePassword123!", required = true)
    private String password;

    //@Email
    @Column(unique = true, nullable = false, length = 100)
    @Schema(description = "Email пользователя", example = "user@example.com", required = true)
    private String email;

    @Column(name = "phone_number", length = 20)
    @Schema(description = "Номер телефона пользователя", example = "+79123456789")
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Дата и время создания пользователя", example = "2023-01-01T12:00:00")
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    @Schema(description = "Дата и время последнего успешного входа", example = "2023-01-02T15:30:00")
    private LocalDateTime lastLogin;

    @Column(name = "last_failed_login")
    @Schema(description = "Дата и время последней неудачной попытки входа", example = "2023-01-02T15:25:00")
    private LocalDateTime lastFailedLogin;

    @Builder.Default
    @Column(name = "failed_login_attempts", nullable = false)
    @Schema(description = "Количество неудачных попыток входа", example = "0")
    private int failedLoginAttempts = 0;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Schema(description = "Дата и время последнего обновления пользователя", example = "2023-01-02T10:00:00")
    private LocalDateTime updatedAt;

    @Column(name = "password_updated_at")
    @Schema(description = "Дата и время последнего изменения пароля", example = "2023-01-01T12:00:00")
    private LocalDateTime passwordUpdatedAt;

    @Column(name = "account_expires_at")
    @Schema(description = "Дата и время истечения срока действия аккаунта", example = "2024-01-01T00:00:00")
    private LocalDateTime accountExpiresAt;

    @Builder.Default
    @Column(name = "account_expired", nullable = false)
    @Schema(description = "Флаг истечения срока действия аккаунта", example = "false")
    private boolean accountExpired = false;

    @Builder.Default
    @Column(name = "credentials_expired", nullable = false)
    @Schema(description = "Флаг истечения срока действия учетных данных", example = "false")
    private boolean credentialsExpired = false;

    @Builder.Default
    @Column(name = "enabled", nullable = false)
    @Schema(description = "Флаг активности аккаунта", example = "true")
    private boolean enabled = true;

    @Builder.Default
    @Column(name = "two_factor_enabled", nullable = false)
    @Schema(description = "Флаг использования двухфакторной аутентификации", example = "false")
    private boolean twoFactorEnabled = false;

    @Column(name = "two_factor_secret", length = 100)
    @Schema(description = "Секрет для двухфакторной аутентификации")
    private String twoFactorSecret;

    @Column(name = "password_reset_token", length = 100)
    @Schema(description = "Токен для сброса пароля")
    private String passwordResetToken;

    @Column(name = "password_reset_token_expiry")
    @Schema(description = "Срок действия токена сброса пароля", example = "2023-01-02T18:00:00")
    private LocalDateTime passwordResetTokenExpiry;

    @Builder.Default
    @Column(name = "email_verified", nullable = false)
    @Schema(description = "Флаг подтверждения email", example = "false")
    private boolean emailVerified = false;

    @Column(name = "email_verification_token", length = 100)
    @Schema(description = "Токен для подтверждения email")
    private String emailVerificationToken;

    @Column(name = "email_verification_token_expiry")
    @Schema(description = "Срок действия токена подтверждения email", example = "2023-01-02T18:00:00")
    private LocalDateTime emailVerificationTokenExpiry;

    @Column(name = "two_factor_recovery_codes", length = 1000)
    @Schema(description = "Коды восстановления для двухфакторной аутентификации (JSON-массив)")
    private String twoFactorRecoveryCodes; // JSON-массив кодов

    @Builder.Default
    @Column(name = "two_factor_verified", nullable = false)
    @Schema(description = "Флаг подтверждения двухфакторной аутентификации", example = "false")
    private boolean twoFactorVerified = false;

    @Builder.Default
    @Column(name = "locked", nullable = false)
    @Schema(description = "Флаг блокировки аккаунта", example = "false")
    private boolean locked = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "fk_users_roles_user"),
            inverseForeignKey = @ForeignKey(name = "fk_users_roles_role")
    )
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 20)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ArraySchema(arraySchema = @Schema(description = "Роли пользователя"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"),
            foreignKey = @ForeignKey(name = "fk_users_groups_user"),
            inverseForeignKey = @ForeignKey(name = "fk_users_groups_group")
    )
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 20)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ArraySchema(arraySchema = @Schema(description = "Группы пользователя"))
    private Set<Group> groups = new HashSet<>();

    @Transient
    @EqualsAndHashCode.Exclude
    @Schema(description = "Привилегии пользователя", accessMode = Schema.AccessMode.READ_ONLY)
    private transient Set<Privilege> privileges;

    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Роли пользователя
        authorities.addAll(roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet()));

        // Привилегии из ролей
        authorities.addAll(getPrivileges().stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                .collect(Collectors.toSet()));

        // Привилегии из групп
        groups.forEach(group ->
                group.getRoles().forEach(role ->
                        authorities.addAll(role.getPrivileges().stream()
                                .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                                .collect(Collectors.toSet()))
                )
        );

        return Collections.unmodifiableSet(authorities);
    }

    public Set<Privilege> getPrivileges() {
        if (privileges == null) {
            privileges = roles.stream()
                    .flatMap(role -> role.getPrivileges().stream())
                    .collect(Collectors.toSet());
        }
        return privileges;
    }

    // Методы управления связями
    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void addGroup(Group group) {
        groups.add(group);
        group.getUsers().add(this);
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired && (accountExpiresAt == null || accountExpiresAt.isAfter(LocalDateTime.now()));
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked && failedLoginAttempts < MAX_FAILED_ATTEMPTS;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    public void resetFailedLoginAttempts() {
        this.failedLoginAttempts = 0;
        this.lastFailedLogin = null;
    }

    public void incrementFailedLoginAttempts() {
        this.failedLoginAttempts++;
        this.lastFailedLogin = LocalDateTime.now();

        // Автоматическая блокировка при превышении лимита
        if (this.failedLoginAttempts >= MAX_FAILED_ATTEMPTS) {
            this.locked = true;
        }
    }
    public void generateRecoveryCodes() {
        List<String> codes = new ArrayList<>();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 10; i++) {
            codes.add(String.format("%06d", random.nextInt(1_000_000)));
        }

        this.twoFactorRecoveryCodes = String.join(",", codes);
    }
}