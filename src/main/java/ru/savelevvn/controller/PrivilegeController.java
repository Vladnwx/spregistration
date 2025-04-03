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
import ru.savelevvn.dto.PrivilegeRequestDTO;
import ru.savelevvn.dto.PrivilegeResponseDTO;
import ru.savelevvn.service.PrivilegeService;

@RestController
@RequestMapping("/api/privileges")
@RequiredArgsConstructor
@Tag(name = "Privilege Management", description = "Endpoints for managing privileges")
public class PrivilegeController {
    private final PrivilegeService privilegeService;

    @Operation(
            summary = "Create a new privilege",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Privilege created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<PrivilegeResponseDTO> createPrivilege(
            @Valid @RequestBody PrivilegeRequestDTO privilegeDTO
    ) {
        return ResponseEntity.ok(privilegeService.createPrivilege(privilegeDTO));
    }

    @Operation(
            summary = "Get privilege by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Privilege found"),
                    @ApiResponse(responseCode = "404", description = "Privilege not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PrivilegeResponseDTO> getPrivilege(@PathVariable Long id) {
        return ResponseEntity.ok(privilegeService.getPrivilegeById(id));
    }

    @Operation(summary = "Get all privileges with pagination")
    @GetMapping
    public ResponseEntity<Page<PrivilegeResponseDTO>> getAllPrivileges(Pageable pageable) {
        return ResponseEntity.ok(privilegeService.getAllPrivileges(pageable));
    }
}