package ru.savelevvn.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.savelevvn.model.Role;
import ru.savelevvn.repository.RoleRepository;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RoleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void createRole_ShouldReturn201() throws Exception {
        String jsonRequest = """
            {
                "name": "ROLE_TEST",
                "description": "Test role"
            }
            """;

        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("ROLE_TEST")));
    }

    @Test
    void getRole_WhenRoleExists_ShouldReturn200() throws Exception {
        Role role = roleRepository.save(new Role(null, "ROLE_EXISTING", "Existing", Set.of()));

        mockMvc.perform(get("/api/roles/" + role.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("ROLE_EXISTING")));
    }
}