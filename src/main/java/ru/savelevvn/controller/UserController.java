package ru.savelevvn.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.savelevvn.dto.ChangePasswordRequest;
import ru.savelevvn.dto.UserRequestDTO;
import ru.savelevvn.dto.UserResponseDTO;
import ru.savelevvn.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Endpoints for managing users and their permissions")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Create a new user",
            description = "Create a new user account. Available only for administrators.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "409", description = "User already exists"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Parameter(description = "User data to create")
            @Valid @RequestBody UserRequestDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @Operation(
            summary = "Update user information",
            description = "Update existing user information. Users can update only their own profile, admins can update any profile.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "ID of the user to update")
            @PathVariable Long id,
            @Parameter(description = "Updated user data")
            @Valid @RequestBody UserRequestDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @Operation(
            summary = "Delete user",
            description = "Delete user account. Available only for administrators.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete")
            @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get user by ID",
            description = "Get user details by ID. Users can view only their own profile, admins can view any profile.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @Cacheable("users")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "ID of the user to retrieve")
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Get all users",
            description = "Get paginated list of all users. Available only for administrators.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @Parameter(description = "Pagination and sorting parameters")
            Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @Operation(
            summary = "Add role to user",
            description = "Add role to user. Available only for administrators.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role added successfully"),
                    @ApiResponse(responseCode = "404", description = "User or role not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserResponseDTO> addRoleToUser(
            @Parameter(description = "ID of the user")
            @PathVariable Long userId,
            @Parameter(description = "ID of the role to add")
            @PathVariable Long roleId) {
        return ResponseEntity.ok(userService.addRoleToUser(userId, roleId));
    }

    @Operation(
            summary = "Remove role from user",
            description = "Remove role from user. Available only for administrators.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role removed successfully"),
                    @ApiResponse(responseCode = "404", description = "User or role not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserResponseDTO> removeRoleFromUser(
            @Parameter(description = "ID of the user")
            @PathVariable Long userId,
            @Parameter(description = "ID of the role to remove")
            @PathVariable Long roleId) {
        return ResponseEntity.ok(userService.removeRoleFromUser(userId, roleId));
    }

    @Operation(
            summary = "Change user password",
            description = "Change user password. Users can change only their own password, admins can change any password.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password changed successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @Parameter(description = "ID of the user")
            @PathVariable Long id,
            @Parameter(description = "Password change request data")
            @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ResponseEntity.noContent().build();
    }
}