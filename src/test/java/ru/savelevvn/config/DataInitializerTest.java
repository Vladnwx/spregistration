package ru.savelevvn.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.savelevvn.model.User;
import ru.savelevvn.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource(properties = {
        "app.admin.username=testadmin",
        "app.admin.password=testpass",
        "app.admin.email=test@example.com"
})
class DataInitializerTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateAdminUser() {
        User admin = userRepository.findByUsername("testadmin")
                .orElseThrow();
        assertThat(admin.getEmail()).isEqualTo("test@example.com");
    }
}