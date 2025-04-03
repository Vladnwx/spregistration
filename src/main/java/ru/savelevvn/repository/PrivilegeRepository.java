package ru.savelevvn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
}