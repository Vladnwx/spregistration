package ru.savelevvn.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.savelevvn.dto.RoleRequestDTO;
import ru.savelevvn.dto.RoleResponseDTO;
import ru.savelevvn.exception.NotFoundException;
import ru.savelevvn.model.Privilege;
import ru.savelevvn.model.Role;
import ru.savelevvn.repository.PrivilegeRepository;
import ru.savelevvn.repository.RoleRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PrivilegeRepository privilegeRepository;

    @InjectMocks
    private ru.savelevvn.service.RoleServiceImpl roleService;

    @Test
    void createRole_ShouldReturnRoleResponseDTO() {
        RoleRequestDTO request = new RoleRequestDTO("ROLE_ADMIN", "Admin role");
        Role savedRole = new Role(1L, "ROLE_ADMIN", "Admin role", Set.of());

        when(roleRepository.save(any(Role.class))).thenReturn(savedRole);

        RoleResponseDTO response = roleService.createRole(request);

        assertEquals("ROLE_ADMIN", response.name());
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void updateRole_WhenRoleExists_ShouldUpdate() {
        Role existingRole = new Role(1L, "ROLE_OLD", "Old desc", Set.of());
        RoleRequestDTO request = new RoleRequestDTO("ROLE_NEW", "New desc");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(existingRole));
        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));

        RoleResponseDTO response = roleService.updateRole(1L, request);

        assertEquals("ROLE_NEW", response.name());
        assertEquals("New desc", response.description());
    }

    @Test
    void updateRole_WhenRoleNotFound_ShouldThrow() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                roleService.updateRole(1L, new RoleRequestDTO("ROLE_NEW", "Desc"))
        );
    }

    @Test
    void addPrivilegeToRole_ShouldAddPrivilege() {
        Role role = new Role(1L, "ROLE_TEST", "Test", Set.of());
        Privilege privilege = new Privilege(1L, "CREATE_USER", "Create users", Set.of());

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(privilegeRepository.findById(1L)).thenReturn(Optional.of(privilege));
        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));

        RoleResponseDTO response = roleService.addPrivilegeToRole(1L, 1L);

        assertTrue(response.privileges().contains("CREATE_USER"));
    }
}