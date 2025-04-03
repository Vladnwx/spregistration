package ru.savelevvn.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.savelevvn.dto.GroupRequestDTO;
import ru.savelevvn.dto.GroupResponseDTO;
import ru.savelevvn.exception.NotFoundException;
import ru.savelevvn.model.*;
import ru.savelevvn.repository.*;
import ru.savelevvn.service.GroupService;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GroupResponseDTO createGroup(GroupRequestDTO groupDTO) {
        Group group = Group.builder()
                .name(groupDTO.name())
                .description(groupDTO.description())
                .system(groupDTO.isSystem())
                .build();
        Group savedGroup = groupRepository.save(group);
        return mapToDTO(savedGroup);
    }

    @Override
    @Transactional
    public GroupResponseDTO updateGroup(Long id, GroupRequestDTO groupDTO) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        group.setName(groupDTO.name());
        group.setDescription(groupDTO.description());
        return mapToDTO(groupRepository.save(group));
    }

    @Override
    @Transactional
    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        if (group.isSystem()) {
            throw new IllegalStateException("System groups cannot be deleted");
        }
        groupRepository.delete(group);
    }

    @Override
    public GroupResponseDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        return mapToDTO(group);
    }

    @Override
    public Page<GroupResponseDTO> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional
    public GroupResponseDTO addRoleToGroup(Long groupId, Long roleId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        group.addRole(role);
        return mapToDTO(groupRepository.save(group));
    }

    @Override
    @Transactional
    public GroupResponseDTO removeRoleFromGroup(Long groupId, Long roleId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        group.getRoles().remove(role);
        // Обновляем роли у всех пользователей группы
        group.getUsers().forEach(user -> user.getRoles().remove(role));
        return mapToDTO(groupRepository.save(group));
    }

    @Override
    @Transactional
    public GroupResponseDTO addUserToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        group.addUser(user); // Метод addUser уже обновляет связи
        return mapToDTO(groupRepository.save(group));
    }

    @Override
    @Transactional
    public GroupResponseDTO removeUserFromGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        group.removeUser(user); // Метод removeUser уже обновляет связи
        return mapToDTO(groupRepository.save(group));
    }

    private GroupResponseDTO mapToDTO(Group group) {
        return new GroupResponseDTO(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.isSystem(),
                group.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                group.getUsers().stream()
                        .map(User::getId)
                        .collect(Collectors.toSet())
        );
    }
}