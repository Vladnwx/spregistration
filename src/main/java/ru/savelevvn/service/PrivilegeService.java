package ru.savelevvn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.savelevvn.dto.PrivilegeRequestDTO;
import ru.savelevvn.dto.PrivilegeResponseDTO;

public interface PrivilegeService {
    PrivilegeResponseDTO createPrivilege(PrivilegeRequestDTO privilegeDTO);
    PrivilegeResponseDTO updatePrivilege(Long id, PrivilegeRequestDTO privilegeDTO);
    void deletePrivilege(Long id);
    PrivilegeResponseDTO getPrivilegeById(Long id);
    Page<PrivilegeResponseDTO> getAllPrivileges(Pageable pageable);
}