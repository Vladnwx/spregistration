package ru.savelevvn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.savelevvn.dto.*;
import ru.savelevvn.dto.RoleResponseDTO;

public interface RoleService {
    RoleResponseDTO createRole(RoleRequestDTO roleDTO);
    RoleResponseDTO updateRole(Long id, RoleRequestDTO roleDTO);
    void deleteRole(Long id);
    RoleResponseDTO getRoleById(Long id);
    Page<RoleResponseDTO> getAllRoles(Pageable pageable);
    RoleResponseDTO addPrivilegeToRole(Long roleId, Long privilegeId);
    RoleResponseDTO removePrivilegeFromRole(Long roleId, Long privilegeId);
}