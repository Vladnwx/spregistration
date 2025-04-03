package ru.savelevvn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private boolean enabled;
    private boolean twoFactorEnabled;
    private Set<String> roles;
    private Set<Long> groups;
}