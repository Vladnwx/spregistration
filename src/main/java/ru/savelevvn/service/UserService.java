package ru.savelevvn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.savelevvn.dto.UserRequestDTO;
import ru.savelevvn.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userDTO);
    UserResponseDTO updateUser(Long id, UserRequestDTO userDTO);
    void deleteUser(Long id);
    UserResponseDTO getUserById(Long id);
    Page<UserResponseDTO> getAllUsers(Pageable pageable);
    UserResponseDTO addRoleToUser(Long userId, Long roleId);
    UserResponseDTO removeRoleFromUser(Long userId, Long roleId);
}