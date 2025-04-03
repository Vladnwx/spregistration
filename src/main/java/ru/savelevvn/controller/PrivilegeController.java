package ru.savelevvn.controller;

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
public class PrivilegeController {
    private final PrivilegeService privilegeService;

    @PostMapping
    public ResponseEntity<PrivilegeResponseDTO> createPrivilege(
            @Valid @RequestBody PrivilegeRequestDTO privilegeDTO
    ) {
        return ResponseEntity.ok(privilegeService.createPrivilege(privilegeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivilegeResponseDTO> getPrivilege(@PathVariable Long id) {
        return ResponseEntity.ok(privilegeService.getPrivilegeById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PrivilegeResponseDTO>> getAllPrivileges(Pageable pageable) {
        return ResponseEntity.ok(privilegeService.getAllPrivileges(pageable));
    }
}