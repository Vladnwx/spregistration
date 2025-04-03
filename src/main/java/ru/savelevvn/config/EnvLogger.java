package ru.savelevvn.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvLogger implements CommandLineRunner {

    private final Environment env;

    @Override
    public void run(String... args) {
        System.out.println("\n=== Проверка переменных окружения ===");
        System.out.println("DB_URL: " + env.getProperty("DB_URL"));
        System.out.println("DB_USER: " + env.getProperty("DB_USER"));
        System.out.println("DB_PASSWORD: " + env.getProperty("DB_PASSWORD"));
        System.out.println("JWT_SECRET: " + (env.getProperty("JWT_SECRET") != null ? "***hidden***" : null));
        System.out.println("ADMIN_USERNAME: " + env.getProperty("ADMIN_USERNAME"));
        System.out.println("ADMIN_PASSWORD: " + (env.getProperty("ADMIN_PASSWORD") != null ? "***hidden***" : null));
        System.out.println("ADMIN_EMAIL: " + (env.getProperty("ADMIN_EMAIL")));
        System.out.println("=== Конец проверки ===\n");
        System.out.println("\n=== Проверка DataSource ===");
        System.out.println("Spring Datasource URL: " + env.getProperty("spring.datasource.url"));
        System.out.println("Hibernate Hikari URL: " + env.getProperty("spring.jpa.properties.hibernate.hikari.dataSource.url"));
        System.out.println("=== Конец проверки ===");
    }

}