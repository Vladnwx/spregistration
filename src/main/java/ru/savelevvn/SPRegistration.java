package ru.savelevvn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // Добавляем поддержку асинхронных методов
public class SPRegistration {
    public static void main(String[] args) {
        SpringApplication.run(SPRegistration.class, args);
    }
}