package ru.savelevvn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savelevvn.dto.*;
import ru.savelevvn.dto.RoleResponseDTO;
import ru.savelevvn.exception.NotFoundException;
import ru.savelevvn.model.Privilege;
import ru.savelevvn.model.Role;
import ru.savelevvn.repository.PrivilegeRepository;
import ru.savelevvn.repository.RoleRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    @Transactional
    @Override
    public RoleResponseDTO createRole(RoleRequestDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.name());
        role.setDescription(roleDTO.description());
        Role savedRole = roleRepository.save(role);
        return RoleResponseDTO.fromEntity(savedRole);
    }

    @Override
    @Transactional
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        role.setName(roleDTO.name());
        role.setDescription(roleDTO.description());
        return RoleResponseDTO.fromEntity(roleRepository.save(role));
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found with id: " + id));
        return mapToResponseDTO(role);
    }

    @Override
    public Page<RoleResponseDTO> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    @Transactional
    public RoleResponseDTO addPrivilegeToRole(Long roleId, Long privilegeId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        Privilege privilege = privilegeRepository.findById(privilegeId)
                .orElseThrow(() -> new NotFoundException("Privilege not found"));
        role.addPrivilege(privilege);
        return mapToResponseDTO(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleResponseDTO removePrivilegeFromRole(Long roleId, Long privilegeId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        Privilege privilege = privilegeRepository.findById(privilegeId)
                .orElseThrow(() -> new NotFoundException("Privilege not found"));
        role.getPrivileges().remove(privilege);
        return mapToResponseDTO(roleRepository.save(role));
    }

    private RoleResponseDTO mapToResponseDTO(Role role) {
        return new RoleResponseDTO(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getPrivileges().stream()
                        .map(Privilege::getName)
                        .collect(Collectors.toSet())
        );
    }
}