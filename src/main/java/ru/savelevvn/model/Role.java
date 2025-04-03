package ru.savelevvn.model;

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
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true, length = 50)
    @Pattern(regexp = "^ROLE_[A-Z_]+$", message = "Role name must start with ROLE_ and be uppercase")
    private String name;

    @Column(length = 200)
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 20)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
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
    private Set<Privilege> privileges = new HashSet<>();

    // Методы для управления связями
    public void addPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
        privilege.getRoles().add(this);
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getRoles().add(this);
    }
}