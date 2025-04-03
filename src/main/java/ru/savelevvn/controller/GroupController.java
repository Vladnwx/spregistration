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
            @Valid @RequestBody GroupRequestDTO groupDTO
    ) {
        return ResponseEntity.ok(groupService.createGroup(groupDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @GetMapping
    public ResponseEntity<Page<GroupResponseDTO>> getAllGroups(Pageable pageable) {
        return ResponseEntity.ok(groupService.getAllGroups(pageable));
    }

    @PostMapping("/{groupId}/roles/{roleId}")
    public ResponseEntity<GroupResponseDTO> addRoleToGroup(
            @PathVariable Long groupId,
            @PathVariable Long roleId
    ) {
        return ResponseEntity.ok(groupService.addRoleToGroup(groupId, roleId));
    }
}