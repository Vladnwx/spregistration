package ru.savelevvn.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createUser_ShouldReturn201() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content("""
                    {
                        "username": "testuser",
                        "password": "securePassword123",
                        "email": "test@example.com",
                        "enabled": true
                    }
                    """))
                .andExpect(status().isCreated());
    }
}