package ru.savelevvn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.savelevvn.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Поиск по названию роли (уникальное поле)
    Optional<Role> findByName(String name);

    // Пагинация ролей
    Page<Role> findAll(Pageable pageable);

    // Проверка существования роли
    boolean existsByName(String name);

    @Query("SELECT r FROM Role r WHERE LOWER(r.name) LIKE LOWER(concat('%', :name, '%'))")
    Page<Role> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("SELECT r FROM Role r JOIN r.privileges p WHERE p.id = :privilegeId")
    Page<Role> findByPrivilegeId(@Param("privilegeId") Long privilegeId, Pageable pageable);

}