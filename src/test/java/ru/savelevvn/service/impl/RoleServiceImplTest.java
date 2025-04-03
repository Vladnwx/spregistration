package ru.savelevvn.service.impl;

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
import java.util.Collections;
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
    private RoleServiceImpl roleService;

    @Test
    void addPrivilegeToRole_ShouldAddPrivilege() {
        Role role = new Role();
        role.setId(1L);

        Privilege privilege = new Privilege();
        privilege.setId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(privilegeRepository.findById(1L)).thenReturn(Optional.of(privilege));

        roleService.addPrivilegeToRole(1L, 1L);

        assertTrue(role.getPrivileges().contains(privilege));
    }
}