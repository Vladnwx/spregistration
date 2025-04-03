package ru.savelevvn.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.savelevvn.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Найти пользователя по email
    Optional<User> findByEmail(String email);

    // Найти пользователя по username
    Optional<User> findByUsername(String username);

    // Проверить существование пользователя по email
    boolean existsByEmail(String email);

    // Получить всех пользователей с пагинацией
    Page<User> findAll(Pageable pageable);

    // Фильтрация пользователей по состоянию enabled/disabled
    Page<User> findByEnabled(boolean enabled, Pageable pageable);

    // Найти пользователей по названию роли (через ManyToMany связь)
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    Page<User> findByRoleName(@Param("roleName") String roleName, Pageable pageable);

    // Обновить пароль пользователя
    @Modifying
    @Query("UPDATE User u SET u.password = :password, u.passwordUpdatedAt = :now WHERE u.id = :id")
    void updatePassword(@Param("id") Long id, @Param("password") String password, @Param("now") LocalDateTime now);

    // Заблокировать/разблокировать пользователя
    @Modifying
    @Query("UPDATE User u SET u.locked = :locked, u.failedLoginAttempts = 0 WHERE u.id = :id")
    void setLockedStatus(@Param("id") Long id, @Param("locked") boolean locked);

    // Обновить последнее время входа
    @Modifying
    @Query("UPDATE User u SET u.lastLogin = :lastLogin WHERE u.id = :id")
    void updateLastLogin(@Param("id") Long id, @Param("lastLogin") LocalDateTime lastLogin);

    // Очистить токен сброса пароля
    @Modifying
    @Query("UPDATE User u SET u.passwordResetToken = NULL, u.passwordResetTokenExpiry = NULL WHERE u.id = :id")
    void clearPasswordResetToken(@Param("id") Long id);

    // Проверить существование пользователя по username
    boolean existsByUsername(
            @NotBlank(message = "Username cannot be blank")
            @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers and underscores")
            String username
    );
    long countByEnabled(boolean enabled);
    long countByLocked(boolean locked);
}