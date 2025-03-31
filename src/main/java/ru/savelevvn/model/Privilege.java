package ru.savelevvn.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "privileges")
public class Privilege { //привилегия

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles = new HashSet<>();
}