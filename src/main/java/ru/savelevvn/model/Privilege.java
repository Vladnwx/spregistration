package ru.savelevvn.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность привилегии (права доступа) в системе")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор привилегии", example = "1")
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true, length = 50)
    @Pattern(regexp = "^[A-Z_]+$", message = "Privilege name must be uppercase with underscores")
    @Schema(
            description = "Уникальное название привилегии (только заглавные буквы и подчеркивания)",
            example = "EDIT_USERS",
            required = true
    )
    private String name;

    @Column(length = 200)
    @Schema(description = "Описание привилегии", example = "Позволяет редактировать данные пользователей")
    private String description;

    @ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 20)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ArraySchema(arraySchema = @Schema(description = "Роли, которые имеют эту привилегию", accessMode = Schema.AccessMode.READ_ONLY))
    private Set<Role> roles = new HashSet<>();

    // Метод для управления связями
    @Schema(hidden = true)
    public void addRole(Role role) {
        this.roles.add(role);
        role.getPrivileges().add(this);
    }

}