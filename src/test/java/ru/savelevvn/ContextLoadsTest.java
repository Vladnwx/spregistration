package ru.savelevvn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class ContextLoadsTest {
    @Test
    void contextLoads() {
        // Пустой тест для проверки поднятия контекста
    }
}
