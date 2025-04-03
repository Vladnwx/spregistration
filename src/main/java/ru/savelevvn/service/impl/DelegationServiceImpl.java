package ru.savelevvn.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savelevvn.dto.DelegationRequestDTO;
import ru.savelevvn.dto.DelegationResponseDTO;
import ru.savelevvn.exception.NotFoundException;
import ru.savelevvn.model.*;
import ru.savelevvn.repository.*;
import ru.savelevvn.service.DelegationService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DelegationServiceImpl implements DelegationService {
    private final DelegationRepository delegationRepository;
    private final UserRepository userRepository;
    private final PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public DelegationResponseDTO createDelegation(DelegationRequestDTO delegationDTO) {
        User delegator = userRepository.findById(delegationDTO.delegatorId())
                .orElseThrow(() -> new NotFoundException("Delegator not found"));
        User delegate = userRepository.findById(delegationDTO.delegateId())
                .orElseThrow(() -> new NotFoundException("Delegate not found"));
        Privilege privilege = privilegeRepository.findById(delegationDTO.privilegeId())
                .orElseThrow(() -> new NotFoundException("Privilege not found"));

        Delegation delegation = Delegation.builder()
                .delegator(delegator)
                .delegate(delegate)
                .privilege(privilege)
                .startTime(delegationDTO.startTime())
                .endTime(delegationDTO.endTime())
                .comment(delegationDTO.comment())
                .active(true)
                .build();

        Delegation savedDelegation = delegationRepository.save(delegation);
        return mapToDTO(savedDelegation);
    }

    @Override
    public DelegationResponseDTO updateDelegation(Long id, DelegationRequestDTO delegationDTO) {
        return null;
    }

    @Override
    @Transactional
    public void cancelDelegation(Long id) {
        Delegation delegation = delegationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Delegation not found"));
        delegation.setActive(false);
        delegationRepository.save(delegation);
    }

    @Override
    public Page<DelegationResponseDTO> getAllDelegations(Pageable pageable) {
        return delegationRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Override
    public Page<DelegationResponseDTO> getActiveDelegationsForUser(Long userId, Pageable pageable) {
        return delegationRepository.findByDelegateIdAndActiveTrueAndEndTimeAfter(userId, LocalDateTime.now(), pageable)
                .map(this::mapToDTO);
    }

    private DelegationResponseDTO mapToDTO(Delegation delegation) {
        return new DelegationResponseDTO(
                delegation.getId(),
                delegation.getDelegator().getId(),
                delegation.getDelegate().getId(),
                delegation.getPrivilege().getId(),
                delegation.getStartTime(),
                delegation.getEndTime(),
                delegation.isActive(),
                delegation.getComment()
        );
    }
}