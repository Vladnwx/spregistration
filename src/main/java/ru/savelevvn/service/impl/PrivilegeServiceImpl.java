package ru.savelevvn.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.savelevvn.dto.PrivilegeRequestDTO;
import ru.savelevvn.dto.PrivilegeResponseDTO;
import ru.savelevvn.exception.NotFoundException;
import ru.savelevvn.model.Privilege;
import ru.savelevvn.repository.PrivilegeRepository;
import ru.savelevvn.service.PrivilegeService;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeRepository privilegeRepository;

    @Override
    public PrivilegeResponseDTO createPrivilege(PrivilegeRequestDTO privilegeDTO) {
        Privilege privilege = new Privilege();
        privilege.setName(privilegeDTO.name());
        privilege.setDescription(privilegeDTO.description());
        Privilege savedPrivilege = privilegeRepository.save(privilege);
        return mapToDTO(savedPrivilege);
    }

    @Override
    public PrivilegeResponseDTO updatePrivilege(Long id, PrivilegeRequestDTO privilegeDTO) {
        Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Privilege not found"));
        privilege.setName(privilegeDTO.name());
        privilege.setDescription(privilegeDTO.description());
        return mapToDTO(privilegeRepository.save(privilege));
    }

    @Override
    public void deletePrivilege(Long id) {
        privilegeRepository.deleteById(id);
    }

    @Override
    public PrivilegeResponseDTO getPrivilegeById(Long id) {
        Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Privilege not found"));
        return mapToDTO(privilege);
    }

    @Override
    public Page<PrivilegeResponseDTO> getAllPrivileges(Pageable pageable) {
        return privilegeRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    private PrivilegeResponseDTO mapToDTO(Privilege privilege) {
        return new PrivilegeResponseDTO(
                privilege.getId(),
                privilege.getName(),
                privilege.getDescription()
        );
    }
}