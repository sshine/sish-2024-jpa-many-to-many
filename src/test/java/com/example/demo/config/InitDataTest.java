package com.example.demo.config;

import com.example.demo.model.Region;
import com.example.demo.repository.KommuneRepository;
import com.example.demo.repository.RegionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;

public class InitDataTest {
    @Mock
    private RegionRepository regionRepository;

    @Mock
    private KommuneRepository kommuneRepository;

    @Mock
    private HttpClient httpClient;

    public InitDataTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchRegions() throws Exception {
        Files.list(Path.of(".")).forEach((file) -> System.out.println(file));

        Path regionsPath = Path.of("data/regioner.json");
        String regionsData = Files.readString(regionsPath);

        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(regionsData);
        when(mockResponse.statusCode()).thenReturn(200);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);

        InitData initData = new InitData(regionRepository, kommuneRepository, httpClient);

        List<Region> regions = initData.fetchRegions();

        Assertions.assertEquals(5, regions.size());
    }

    // TODO: testFetchKommuner()

}
