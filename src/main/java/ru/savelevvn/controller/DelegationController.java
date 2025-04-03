package ru.savelevvn.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Delegation Management", description = "Endpoints for managing delegations")
public class DelegationController {
    private final DelegationService delegationService;

    @Operation(
            summary = "Create a new delegation",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delegation created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<DelegationResponseDTO> createDelegation(
            @Valid @RequestBody DelegationRequestDTO delegationDTO
    ) {
        return ResponseEntity.ok(delegationService.createDelegation(delegationDTO));
    }

    @Operation(
            summary = "Cancel a delegation",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Delegation cancelled successfully"),
                    @ApiResponse(responseCode = "404", description = "Delegation not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelDelegation(@PathVariable Long id) {
        delegationService.cancelDelegation(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all delegations with pagination")
    @GetMapping
    public ResponseEntity<Page<DelegationResponseDTO>> getAllDelegations(Pageable pageable) {
        return ResponseEntity.ok(delegationService.getAllDelegations(pageable));
    }
}