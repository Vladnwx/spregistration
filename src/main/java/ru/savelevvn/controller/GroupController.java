package ru.savelevvn.controller;

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
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponseDTO> createGroup(
            @Valid @RequestBody GroupRequestDTO groupDTO) {
        return ResponseEntity.ok(groupService.createGroup(groupDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> updateGroup(
            @PathVariable Long id,
            @Valid @RequestBody GroupRequestDTO groupDTO) {
        return ResponseEntity.ok(groupService.updateGroup(id, groupDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @GetMapping
    public ResponseEntity<Page<GroupResponseDTO>> getAllGroups(Pageable pageable) {
        return ResponseEntity.ok(groupService.getAllGroups(pageable));
    }

    @PostMapping("/{groupId}/roles/{roleId}")
    public ResponseEntity<GroupResponseDTO> addRoleToGroup(
            @PathVariable Long groupId,
            @PathVariable Long roleId) {
        return ResponseEntity.ok(groupService.addRoleToGroup(groupId, roleId));
    }

    @DeleteMapping("/{groupId}/roles/{roleId}")
    public ResponseEntity<GroupResponseDTO> removeRoleFromGroup(
            @PathVariable Long groupId,
            @PathVariable Long roleId) {
        return ResponseEntity.ok(groupService.removeRoleFromGroup(groupId, roleId));
    }

    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<GroupResponseDTO> addUserToGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(groupService.addUserToGroup(groupId, userId));
    }

    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<GroupResponseDTO> removeUserFromGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(groupService.removeUserFromGroup(groupId, userId));
    }
}