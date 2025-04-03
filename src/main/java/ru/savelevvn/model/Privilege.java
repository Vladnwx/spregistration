package ru.savelevvn.model;

import jakarta.persistence.*;
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
@Table(name = "privileges")
@SQLDelete(sql = "UPDATE privileges SET name = CONCAT(name, '_DELETED_', CURRENT_TIMESTAMP) WHERE id = ?")
@Where(clause = "name NOT LIKE '%_DELETED_%'")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true, length = 50)
    @Pattern(regexp = "^[A-Z_]+$", message = "Privilege name must be uppercase with underscores")
    private String name;

    @Column(length = 200)
    private String description;

    @ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 20)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    // Метод для управления связями
    public void addRole(Role role) {
        this.roles.add(role);
        role.getPrivileges().add(this);
    }

}