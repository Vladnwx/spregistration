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
public class DelegationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createDelegation_ShouldReturn201() throws Exception {
        mockMvc.perform(post("/api/delegations")
                        .contentType("application/json")
                        .content("""
                    {
                        "delegatorId": 1,
                        "delegateId": 2,
                        "privilegeId": 1,
                        "startTime": "2025-01-01T10:00:00",
                        "endTime": "2025-01-10T10:00:00",
                        "comment": "Test delegation"
                    }
                    """))
                .andExpect(status().isCreated());
    }
}