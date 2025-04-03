package ru.savelevvn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.savelevvn.model.Group;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    // Поиск по названию группы (уникальное поле)
    Optional<Group> findByName(String name);

    // Пагинация групп
    Page<Group> findAll(Pageable pageable);

    // Фильтрация по системным группам
    Page<Group> findBySystem(boolean isSystem, Pageable pageable);
}