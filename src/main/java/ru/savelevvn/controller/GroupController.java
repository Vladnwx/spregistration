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
import ru.savelevvn.dto.GroupRequestDTO;
import ru.savelevvn.dto.GroupResponseDTO;
import ru.savelevvn.service.GroupService;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Tag(name = "Group Management", description = "Endpoints for managing groups, their roles and users")
public class GroupController {
    private final GroupService groupService;

    @Operation(
            summary = "Create a new group",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Group created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<GroupResponseDTO> createGroup(
            @Valid @RequestBody GroupRequestDTO groupDTO) {
        return ResponseEntity.ok(groupService.createGroup(groupDTO));
    }

    @Operation(
            summary = "Update an existing group",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Group updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Group not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> updateGroup(
            @PathVariable Long id,
            @Valid @RequestBody GroupRequestDTO groupDTO) {
        return ResponseEntity.ok(groupService.updateGroup(id, groupDTO));
    }

    @Operation(
            summary = "Delete a group",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Group deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Group not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get group by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Group found"),
                    @ApiResponse(responseCode = "404", description = "Group not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @Operation(summary = "Get all groups with pagination")
    @GetMapping
    public ResponseEntity<Page<GroupResponseDTO>> getAllGroups(Pageable pageable) {
        return ResponseEntity.ok(groupService.getAllGroups(pageable));
    }

    @Operation(
            summary = "Add role to group",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role added successfully"),
                    @ApiResponse(responseCode = "404", description = "Group or role not found")
            }
    )
    @PostMapping("/{groupId}/roles/{roleId}")
    public ResponseEntity<GroupResponseDTO> addRoleToGroup(
            @PathVariable Long groupId,
            @PathVariable Long roleId) {
        return ResponseEntity.ok(groupService.addRoleToGroup(groupId, roleId));
    }

    @Operation(
            summary = "Remove role from group",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role removed successfully"),
                    @ApiResponse(responseCode = "404", description = "Group or role not found")
            }
    )
    @DeleteMapping("/{groupId}/roles/{roleId}")
    public ResponseEntity<GroupResponseDTO> removeRoleFromGroup(
            @PathVariable Long groupId,
            @PathVariable Long roleId) {
        return ResponseEntity.ok(groupService.removeRoleFromGroup(groupId, roleId));
    }

    @Operation(
            summary = "Add user to group",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User added successfully"),
                    @ApiResponse(responseCode = "404", description = "Group or user not found")
            }
    )
    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<GroupResponseDTO> addUserToGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(groupService.addUserToGroup(groupId, userId));
    }

    @Operation(
            summary = "Remove user from group",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User removed successfully"),
                    @ApiResponse(responseCode = "404", description = "Group or user not found")
            }
    )
    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<GroupResponseDTO> removeUserFromGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(groupService.removeUserFromGroup(groupId, userId));
    }
}