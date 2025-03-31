package ru.savelevvn.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_username", columnList = "username")
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password; 

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; //дата и время создания аккаунта

    @Column(name = "last_login")
    private LocalDateTime lastLogin; //последний успешный логин

    @Column(name = "last_failed_login")
    private LocalDateTime lastFailedLogin; //последний неудачный логин

    @Column(name = "failed_login_attempts", nullable = false)
    private int failedLoginAttempts = 0; //количество неудачных попыток входа

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; //время последнего обновления записи


    @Column(name = "password_updated_at")
    private LocalDateTime passwordUpdatedAt; //время последнего обновления пароля


    @Column(name = "account_expires_at")
    private LocalDateTime accountExpiresAt; //время истечения аккаунта

    @Column(name = "account_expired", nullable = false)
    private boolean accountExpired = false; //аккаунт истек


    @Column(name = "credentials_expired", nullable = false)
    private boolean credentialsExpired = false; //аккаунт истек


    @Column(name = "enabled", nullable = false)
    private boolean enabled = true; //аккунт активен


    @Column(name = "two_factor_enabled", nullable = false)
    private boolean twoFactorEnabled = false; //двухфакторная аутентификация включена

    @Column(name = "two_factor_secret")
    private String twoFactorSecret; //секретный код для двухфакторной аутентификации


    @Column(name = "password_reset_token")
    private String passwordResetToken; //токен для сброса пароля


    @Column(name = "password_reset_token_expiry")
    private LocalDateTime passwordResetTokenExpiry; //срок действия токена для сброса пароля


    @Column(name = "role", nullable = false)
    private String role = "ROLE_USER"; //роль пользователя


    @Override
    public boolean isAccountNonExpired() { //аккаунт не истекает
        return !accountExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() { //
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() { //аккаунт активен

        return enabled;
    }

    @ManyToMany(fetch = FetchType.EAGER) //отношение многие ко многим с таблицей ролей и привилегий
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>(); //список ролей пользователя


    @Transient
    private Set<Privilege> privileges; //список привилегий пользователя


    private Set<Group> groups = new HashSet<>(); //список групп пользователя


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //возвращает список ролей и привилегий пользователя

        Set<GrantedAuthority> authorities = new HashSet<>();

        // Добавляем привилегии из ролей пользователя
        authorities.addAll(getPrivileges().stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                .collect(Collectors.toSet()));

        // Добавляем привилегии из групповых ролей
        for (Group group : groups) {
            for (Role role : group.getRoles()) {
                authorities.addAll(role.getPrivileges().stream()
                        .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                        .collect(Collectors.toSet()));
            }
        }

        return authorities;
    }

    public Set<Privilege> getPrivileges() { //возвращает список привилегий пользователя

        if (privileges == null) {
            privileges = new HashSet<>();
            for (Role role : roles) {
                privileges.addAll(role.getPrivileges());
            }
        }
        return privileges;
    }

    public boolean hasPrivilege(String privilegeName) { //проверяет наличие привилегии у пользователя

        return getPrivileges().stream()
                .anyMatch(p -> p.getName().equals(privilegeName));
    }

}