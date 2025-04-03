package ru.savelevvn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.savelevvn.model.Delegation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DelegationRepository extends JpaRepository<Delegation, Long> {

    // Поиск активных делегаций для пользователя
    @Query("SELECT d FROM Delegation d WHERE d.delegate.id = :userId AND d.active = true AND d.endTime > CURRENT_TIMESTAMP")
    List<Delegation> findActiveDelegationsForUser(@Param("userId") Long userId);

    // Пагинация делегаций
    Page<Delegation> findAll(Pageable pageable);

    // Делегации по привилегии
    Page<Delegation> findByPrivilegeId(Long privilegeId, Pageable pageable);

    // Делегации, истекающие в указанный период
    @Query("SELECT d FROM Delegation d WHERE d.endTime BETWEEN :start AND :end")
    Page<Delegation> findExpiringDelegations(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );
}