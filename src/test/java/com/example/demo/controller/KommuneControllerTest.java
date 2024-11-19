package com.example.demo.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class KommuneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetKommunerByRegion() throws Exception {
        // Positive tests
        String[] regionskoder = new String[]{
                "1081", "1082", "1083", "1084", "1085"
        };

        for (String regionskode : regionskoder) {
            mockMvc
                .perform(get("/kommuner").param("regionskode", regionskode))
                .andExpect(status().isOk())

                // Test that the first and second kommune's region has the queried regionskode.
                .andExpect(jsonPath("$[0].region.kode").value(regionskode))
                .andExpect(jsonPath("$[1].region.kode").value(regionskode))

                // Instead of testing only $[0] and $[1], test this for ALL objects!
                .andExpect(jsonPath("$[*].region.kode")
                    .value(Matchers.everyItem(Matchers.equalTo(regionskode))));
        }

        // Negative tests
        mockMvc
            .perform(get("/kommuner").param("regionskode", "123456789"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetRegionerUnique() throws Exception {
        mockMvc
            .perform(get("/regioner"))
            .andExpect(status().isOk());
    }

    // TODO: Test at /regioner returnerer objekter med unikke regionskoder
    // TODO: Test at ALLE regioner har egenskaben fra `testGetKommunerByRegion`
}
