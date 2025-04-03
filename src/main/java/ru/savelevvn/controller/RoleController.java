package ru.savelevvn.controller;

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
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO RoleRequestDTO) {
        return ResponseEntity.ok(roleService.createRole(RoleRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequestDTO RoleRequestDTO
    ) {
        return ResponseEntity.ok(roleService.updateRole(id, RoleRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping
    public ResponseEntity<Page<RoleResponseDTO>> getAllRoles(Pageable pageable) {
        return ResponseEntity.ok(roleService.getAllRoles(pageable));
    }

    @PostMapping("/{roleId}/privileges/{privilegeId}")
    public ResponseEntity<RoleResponseDTO> addPrivilegeToRole(
            @PathVariable Long roleId,
            @PathVariable Long privilegeId
    ) {
        return ResponseEntity.ok(roleService.addPrivilegeToRole(roleId, privilegeId));
    }

    @DeleteMapping("/{roleId}/privileges/{privilegeId}")
    public ResponseEntity<RoleResponseDTO> removePrivilegeFromRole(
            @PathVariable Long roleId,
            @PathVariable Long privilegeId
    ) {
        return ResponseEntity.ok(roleService.removePrivilegeFromRole(roleId, privilegeId));
    }
}