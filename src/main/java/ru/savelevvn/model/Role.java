package ru.savelevvn.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@SQLDelete(sql = "UPDATE roles SET name = CONCAT(name, '_DELETED_', CURRENT_TIMESTAMP) WHERE id = ?")
@Where(clause = "name NOT LIKE '%_DELETED_%'")
@Schema(description = "Сущность роли пользователя в системе")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор роли", example = "1")
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true, length = 50)
    @Pattern(regexp = "^ROLE_[A-Z_]+$", message = "Role name must start with ROLE_ and be uppercase")
    @Schema(
            description = "Уникальное название роли (должно начинаться с ROLE_ и содержать только заглавные буквы и подчеркивания)",
            example = "ROLE_ADMIN",
            required = true
    )
    private String name;

    @Column(length = 200)
    @Schema(description = "Описание роли", example = "Администратор системы с полными правами")
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 20)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ArraySchema(arraySchema = @Schema(description = "Пользователи, имеющие эту роль", accessMode = Schema.AccessMode.READ_ONLY))
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"),
            foreignKey = @ForeignKey(name = "fk_roles_privileges_role"),
            inverseForeignKey = @ForeignKey(name = "fk_roles_privileges_privilege")
    )
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 20)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ArraySchema(arraySchema = @Schema(description = "Привилегии, связанные с этой ролью"))
    private Set<Privilege> privileges = new HashSet<>();

    // Методы для управления связями
    @Schema(hidden = true) // Скрываем методы из документации API
    public void addPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
        privilege.getRoles().add(this);
    }
    @Schema(hidden = true) // Скрываем методы из документации API
    public void addUser(User user) {
        this.users.add(user);
        user.getRoles().add(this);
    }

    @PrePersist
    @PreUpdate
    private void validateName() {
        if (!name.startsWith("ROLE_") || !name.matches("^ROLE_[A-Z_]+$")) {
            throw new IllegalArgumentException("Role name must start with ROLE_ and contain only uppercase letters and underscores");
        }
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges.clear();
        this.privileges.addAll(privileges);
        privileges.forEach(p -> p.getRoles().add(this));
    }
}