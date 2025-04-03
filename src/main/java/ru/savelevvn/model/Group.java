package ru.savelevvn.model;

import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
@SQLDelete(sql = "UPDATE groups SET name = CONCAT(name, '_DELETED_', CURRENT_TIMESTAMP) WHERE id = ?")
@Where(clause = "name NOT LIKE '%_DELETED_%'")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_roles",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "fk_group_roles_group"),
            inverseForeignKey = @ForeignKey(name = "fk_group_roles_role")
    )
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 20)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 20)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<User> users = new HashSet<>();

    @Column(name = "is_system", nullable = false)
    @Builder.Default
    private boolean system = false;

    // Методы для управления связями
    public void addRole(Role role) {
        this.roles.add(role);
        // Обновляем связь на стороне Role через пользователей
        this.users.forEach(user -> {
            user.getRoles().add(role);
            role.getUsers().add(user);
        });
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getGroups().add(this);
        // Автоматически добавляем роли группы пользователю
        this.roles.forEach(role -> {
            user.getRoles().add(role);
            role.getUsers().add(user);
        });
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getGroups().remove(this);
        // Удаляем только те роли, которые есть в группе
        this.roles.forEach(role -> {
            user.getRoles().remove(role);
            role.getUsers().remove(user);
        });
    }

    // Проверка наличия роли в группе
    public boolean hasRole(Role role) {
        return this.roles.contains(role);
    }

    // Проверка наличия привилегии в группе
    public boolean hasPrivilege(String privilegeName) {
        return this.roles.stream()
                .flatMap(role -> role.getPrivileges().stream())
                .anyMatch(privilege -> privilege.getName().equals(privilegeName));
    }

    // Метод для проверки, можно ли изменять группу
    public boolean isEditable() {
        return !system; // Системные группы нельзя редактировать
    }
}