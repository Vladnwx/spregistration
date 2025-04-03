package ru.savelevvn.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.savelevvn.model.User;
import ru.savelevvn.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DataInitializerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateAdminUser() {
        User admin = userRepository.findByUsername("admin")
                .orElseThrow();

        assertThat(admin.getEmail()).isEqualTo("admin@example.com");
        assertThat(admin.isEnabled()).isTrue();
        assertThat(admin.getRoles())
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }
}