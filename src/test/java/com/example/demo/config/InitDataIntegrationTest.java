package com.example.demo.config;

import com.example.demo.model.Kommune;
import com.example.demo.model.Region;
import com.example.demo.repository.KommuneRepository;
import com.example.demo.repository.RegionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;

import java.net.http.HttpClient;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InitDataIntegrationTest {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private KommuneRepository kommuneRepository;

    @Autowired
    private InitData initData;

    @Test
    void testRunSavesRegionsAndKommuner() throws Exception {
        List<Region> regions = regionRepository.findAll();
        Assertions.assertEquals(5, regions.size());

        List<Kommune> kommuner = kommuneRepository.findAll();
        Assertions.assertEquals(99, kommuner.size());
    }

    // Mock også HttpClient når vi tester databasen.
    @TestConfiguration
    static class MockHttpClientConfig {
        @Bean
        public HttpClient httpClient() {
            return Mockito.mock(HttpClient.class);
        }
    }
}
