package ru.savelevvn.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savelevvn.dto.UserRequestDTO;
import ru.savelevvn.dto.UserResponseDTO;
import ru.savelevvn.exception.NotFoundException;
import ru.savelevvn.model.*;
import ru.savelevvn.repository.*;
import ru.savelevvn.service.UserService;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userDTO) {
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

    private UserResponseDTO mapToDTO(User user) {
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
}