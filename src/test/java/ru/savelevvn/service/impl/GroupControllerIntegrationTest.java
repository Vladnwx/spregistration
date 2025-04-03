package ru.savelevvn.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.savelevvn.repository.GroupRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    void createGroup_ShouldReturn201() throws Exception {
        mockMvc.perform(post("/api/groups")
                        .contentType("application/json")
                        .content("""
                    {
                        "name": "Developers",
                        "description": "Dev team",
                        "isSystem": false
                    }
                    """))
                .andExpect(status().isCreated());
    }
}