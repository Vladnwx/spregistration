package ru.savelevvn.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.savelevvn.model.*;
import ru.savelevvn.repository.*;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {
    @Mock private GroupRepository groupRepo;
    @Mock private RoleRepository roleRepo;
    @Mock private UserRepository userRepo;

    @InjectMocks private GroupServiceImpl groupService;

    @Test
    void addRoleToGroup_ShouldAddRole() {
        // Given
        Group group = new Group();
        Role role = new Role();

        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(roleRepo.findById(1L)).thenReturn(Optional.of(role));

        // When
        groupService.addRoleToGroup(1L, 1L);

        // Then
        assertTrue(group.getRoles().contains(role));
    }
}