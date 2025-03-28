package ru.savelevvn.service;
import ru.savelevvn.model.User;
import java.util.List;
import java.util.Optional;
public interface UserService {
    User registerUser(String username, String email, String password)
            throws IllegalArgumentException;
    User create(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    User update(User user);
    void delete(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
