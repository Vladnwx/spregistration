package ru.savelevvn.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savelevvn.dto.DelegationRequestDTO;
import ru.savelevvn.dto.DelegationResponseDTO;
import ru.savelevvn.service.DelegationService;

@RestController
@RequestMapping("/api/delegations")
@RequiredArgsConstructor
public class DelegationController {
    private final DelegationService delegationService;

    @PostMapping
    public ResponseEntity<DelegationResponseDTO> createDelegation(
            @Valid @RequestBody DelegationRequestDTO delegationDTO
    ) {
        return ResponseEntity.ok(delegationService.createDelegation(delegationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelDelegation(@PathVariable Long id) {
        delegationService.cancelDelegation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DelegationResponseDTO>> getAllDelegations(Pageable pageable) {
        return ResponseEntity.ok(delegationService.getAllDelegations(pageable));
    }
}