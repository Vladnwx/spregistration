package ru.savelevvn.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.savelevvn.dto.SystemStatistics;
import ru.savelevvn.service.UserService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Administration", description = "Admin-only management endpoints")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;


    @Operation(
            summary = "Lock/unlock user account",
            description = "Toggle user lock status (Admin only)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Operation successful"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PostMapping("/users/{id}/lock")
    public ResponseEntity<Void> toggleUserLock(
            @PathVariable Long id,
            @RequestParam boolean locked) {
        userService.setUserLockStatus(id, locked);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get system statistics",
            description = "Retrieve system statistics (Admin only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Statistics retrieved"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @GetMapping("/statistics")
    public ResponseEntity<SystemStatistics> getSystemStatistics() {
        return ResponseEntity.ok(userService.getSystemStatistics());
    }
}