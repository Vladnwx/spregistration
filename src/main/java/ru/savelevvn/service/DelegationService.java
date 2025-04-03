package ru.savelevvn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.savelevvn.dto.DelegationRequestDTO;
import ru.savelevvn.dto.DelegationResponseDTO;

public interface DelegationService {
    DelegationResponseDTO createDelegation(DelegationRequestDTO delegationDTO);
    DelegationResponseDTO updateDelegation(Long id, DelegationRequestDTO delegationDTO);
    void cancelDelegation(Long id);
    Page<DelegationResponseDTO> getAllDelegations(Pageable pageable);
    Page<DelegationResponseDTO> getActiveDelegationsForUser(Long userId, Pageable pageable);
}