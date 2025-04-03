package ru.savelevvn.repository;

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

    // Поиск по email (уникальное поле)
    Optional<User> findByEmail(String email);

    // Поиск по username (уникальное поле)
    Optional<User> findByUsername(String username);

    // Проверка существования email
    boolean existsByEmail(String email);

    // Получение пользователей с пагинацией
    Page<User> findAll(Pageable pageable);

    // Фильтрация по enabled/disabled
    Page<User> findByEnabled(boolean enabled, Pageable pageable);

    // Поиск по роли (через связь ManyToMany)
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    Page<User> findByRoleName(@Param("roleName") String roleName, Pageable pageable);

    // Обновление пароля
    @Modifying
    @Query("UPDATE User u SET u.password = :password, u.passwordUpdatedAt = :now WHERE u.id = :id")
    void updatePassword(@Param("id") Long id, @Param("password") String password, @Param("now") LocalDateTime now);

    // Блокировка/разблокировка пользователя
    @Modifying
    @Query("UPDATE User u SET u.locked = :locked, u.failedLoginAttempts = 0 WHERE u.id = :id")
    void setLockedStatus(@Param("id") Long id, @Param("locked") boolean locked);

    // Обновление последнего входа
    @Modifying
    @Query("UPDATE User u SET u.lastLogin = :lastLogin WHERE u.id = :id")
    void updateLastLogin(@Param("id") Long id, @Param("lastLogin") LocalDateTime lastLogin);

    // Сброс токена сброса пароля
    @Modifying
    @Query("UPDATE User u SET u.passwordResetToken = NULL, u.passwordResetTokenExpiry = NULL WHERE u.id = :id")
    void clearPasswordResetToken(@Param("id") Long id);
}