package ru.savelevvn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}

//curl -u admin:adminadmin http://localhost:8080/v3/api-docs.yaml -o src/main/resources/api/openapi.yml