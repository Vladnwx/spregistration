package ru.savelevvn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.savelevvn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.savelevvn.model.UserRole;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Modifying
    @Query("UPDATE User u SET u.accountNonLocked = :status WHERE u.id = :userId")
    void updateAccountLockStatus(@Param("userId") Long userId, @Param("status") boolean status);
    List<User> findByRole(UserRole role);
    Page<User> findAll(Pageable pageable);
}
