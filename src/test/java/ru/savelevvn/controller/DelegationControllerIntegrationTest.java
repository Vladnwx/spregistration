package ru.savelevvn.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.savelevvn.config.BaseIntegrationTest;
import ru.savelevvn.repository.DelegationRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DelegationControllerIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private DelegationRepository delegationRepo;

    @Test
    void createDelegation_ShouldReturn201() throws Exception {
        String json = """
        {
            "delegatorId": 1,
            "delegateId": 2,
            "privilegeId": 1,
            "startTime": "2025-01-01T10:00:00",
            "endTime": "2025-01-10T10:00:00"
        }
        """;

        mockMvc.perform(post("/api/delegations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}