package ru.savelevvn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.savelevvn.dto.GroupRequestDTO;
import ru.savelevvn.dto.GroupResponseDTO;

public interface GroupService {
    GroupResponseDTO createGroup(GroupRequestDTO groupDTO);
    GroupResponseDTO updateGroup(Long id, GroupRequestDTO groupDTO);
    void deleteGroup(Long id);
    GroupResponseDTO getGroupById(Long id);
    Page<GroupResponseDTO> getAllGroups(Pageable pageable);
    GroupResponseDTO addRoleToGroup(Long groupId, Long roleId);
    GroupResponseDTO removeRoleFromGroup(Long groupId, Long roleId);
    GroupResponseDTO addUserToGroup(Long groupId, Long userId);
    GroupResponseDTO removeUserFromGroup(Long groupId, Long userId);
}