package ru.savelevvn.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savelevvn.dto.RoleRequestDTO;
import ru.savelevvn.dto.RoleResponseDTO;
import ru.savelevvn.service.RoleService;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role Management", description = "Endpoints for managing roles and their privileges")
public class RoleController {

    private final RoleService roleService;

    @Operation(
            summary = "Create a new role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO RoleRequestDTO) {
        return ResponseEntity.ok(roleService.createRole(RoleRequestDTO));
    }

    @Operation(
            summary = "Update an existing role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Role not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequestDTO RoleRequestDTO
    ) {
        return ResponseEntity.ok(roleService.updateRole(id, RoleRequestDTO));
    }

    @Operation(
            summary = "Delete a role",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Role not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get role by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role found"),
                    @ApiResponse(responseCode = "404", description = "Role not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @Operation(summary = "Get all roles with pagination")
    @GetMapping
    public ResponseEntity<Page<RoleResponseDTO>> getAllRoles(Pageable pageable) {
        return ResponseEntity.ok(roleService.getAllRoles(pageable));
    }

    @Operation(
            summary = "Add privilege to role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Privilege added successfully"),
                    @ApiResponse(responseCode = "404", description = "Role or privilege not found")
            }
    )
    @PostMapping("/{roleId}/privileges/{privilegeId}")
    public ResponseEntity<RoleResponseDTO> addPrivilegeToRole(
            @PathVariable Long roleId,
            @PathVariable Long privilegeId
    ) {
        return ResponseEntity.ok(roleService.addPrivilegeToRole(roleId, privilegeId));
    }

    @Operation(
            summary = "Remove privilege from role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Privilege removed successfully"),
                    @ApiResponse(responseCode = "404", description = "Role or privilege not found")
            }
    )
    @DeleteMapping("/{roleId}/privileges/{privilegeId}")
    public ResponseEntity<RoleResponseDTO> removePrivilegeFromRole(
            @PathVariable Long roleId,
            @PathVariable Long privilegeId
    ) {
        return ResponseEntity.ok(roleService.removePrivilegeFromRole(roleId, privilegeId));
    }
}