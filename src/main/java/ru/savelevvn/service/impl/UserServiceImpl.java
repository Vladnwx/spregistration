package ru.savelevvn.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savelevvn.dto.*;
import ru.savelevvn.exception.NotFoundException;
import ru.savelevvn.exception.PasswordMismatchException;
import ru.savelevvn.exception.UserAlreadyExistsException;
import ru.savelevvn.model.*;
import ru.savelevvn.repository.*;
import ru.savelevvn.service.UserService;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.username())) {
            throw new UserAlreadyExistsException("username", userDTO.username());
        }

        if (userRepository.existsByEmail(userDTO.email())) {
            throw new UserAlreadyExistsException("email", userDTO.email());
        }

        User user = User.builder()
                .username(userDTO.username())
                .password(passwordEncoder.encode(userDTO.password()))
                .email(userDTO.email())
                .phoneNumber(userDTO.phoneNumber())
                .enabled(userDTO.enabled())
                .twoFactorEnabled(userDTO.twoFactorEnabled())
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setUsername(userDTO.username());
        if (userDTO.password() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.password()));
        }
        user.setEmail(userDTO.email());
        user.setPhoneNumber(userDTO.phoneNumber());
        user.setEnabled(userDTO.enabled());
        user.setTwoFactorEnabled(userDTO.twoFactorEnabled());

        return mapToDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return mapToDTO(user);
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional
    public UserResponseDTO addRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        user.addRole(role);
        return mapToDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDTO removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        user.getRoles().remove(role);
        return mapToDTO(userRepository.save(user));
    }

    public UserResponseDTO mapToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                user.isEnabled(),
                user.isTwoFactorEnabled(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                user.getGroups().stream()
                        .map(Group::getId)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    // Добавим метод для обновления данных аутентификации
    @Transactional
    public void updateAuthenticationData(Long userId, LocalDateTime lastLogin, int failedAttempts, boolean locked) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setLastLogin(lastLogin);
            user.setFailedLoginAttempts(failedAttempts);
            user.setLocked(locked);
            userRepository.save(user);
        });
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
    }
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    public SystemStatistics getSystemStatistics() {
        return SystemStatistics.builder()
                .totalUsers(userRepository.count())
                .activeUsers(userRepository.countByEnabled(true))
                .lockedUsers(userRepository.countByLocked(true))
                .build();
    }

    @Override
    @Transactional
    public UserResponseDTO updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        return mapToDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public void setUserLockStatus(Long userId, boolean locked) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setLocked(locked);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new PasswordMismatchException();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

}