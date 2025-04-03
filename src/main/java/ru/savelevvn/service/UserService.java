package ru.savelevvn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.savelevvn.dto.*;
import ru.savelevvn.model.User;

import java.time.LocalDateTime;

//Сервис для работы с пользователями.
public interface UserService {

    // Создание нового пользователя.
    UserResponseDTO createUser(UserRequestDTO userDTO);

    // Обновление данных пользователя.
    UserResponseDTO updateUser(Long id, UserRequestDTO userDTO);

    // Удаление пользователя.
    void deleteUser(Long id);

    // Получение данных пользователя по ID.
    UserResponseDTO getUserById(Long id);

    // Получение списка всех пользователей с пагинацией.
    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    // Назначение роли пользователю.
    UserResponseDTO addRoleToUser(Long userId, Long roleId);

    // Снятие роли с пользователя.
    UserResponseDTO removeRoleFromUser(Long userId, Long roleId);

    // Загрузка данных пользователя по имени (Spring Security).
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    // Поиск пользователя по электронной почте.
    User findByEmail(String email);

    // Обновление данных аутентификации пользователя.
    void updateAuthenticationData(Long userId, LocalDateTime lastLogin, int failedAttempts, boolean locked);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByUsername(String username);
    SystemStatistics getSystemStatistics();

    UserResponseDTO updateProfile(Long userId, UpdateProfileRequest request);

    void setUserLockStatus(Long userId, boolean locked);

    UserResponseDTO mapToDTO(User user);

    @Transactional
    void changePassword(Long userId, ChangePasswordRequest request);

}