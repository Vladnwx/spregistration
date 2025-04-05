package ru.savelevvn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.savelevvn.model.Privilege;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    // Поиск по названию привилегии (уникальное поле)
    Optional<Privilege> findByName(String name);

    // Пагинация привилегий
    Page<Privilege> findAll(Pageable pageable);

    // Проверка существования привилегии
    boolean existsByName(String name);

    @Query("SELECT p FROM Privilege p WHERE LOWER(p.name) LIKE LOWER(concat('%', :name, '%'))")
    Page<Privilege> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("SELECT COUNT(r) > 0 FROM Role r JOIN r.privileges p WHERE p.id = :privilegeId")
    boolean isPrivilegeAssignedToAnyRole(@Param("privilegeId") Long privilegeId);

}