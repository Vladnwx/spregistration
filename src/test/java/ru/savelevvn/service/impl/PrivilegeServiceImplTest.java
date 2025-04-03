package ru.savelevvn.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.savelevvn.model.Privilege;
import ru.savelevvn.repository.PrivilegeRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrivilegeServiceImplTest {

    @Mock private PrivilegeRepository privilegeRepository;
    @InjectMocks private PrivilegeServiceImpl privilegeService;

    @Test
    void getPrivilegeById_ShouldReturnPrivilege() {
        Privilege privilege = new Privilege();
        privilege.setId(1L);
        privilege.setName("CREATE");
        privilege.setDescription("Create");
        when(privilegeRepository.findById(1L)).thenReturn(Optional.of(privilege));

        var result = privilegeService.getPrivilegeById(1L);

        assertEquals("CREATE", result.name());
    }

    @Test
    void deletePrivilege_ShouldCallRepository() {
        privilegeService.deletePrivilege(1L);
        verify(privilegeRepository).deleteById(1L);
    }
}