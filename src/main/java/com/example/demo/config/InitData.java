package com.example.demo.config;

import com.example.demo.model.Kommune;
import com.example.demo.model.Region;
import com.example.demo.repository.KommuneRepository;
import com.example.demo.repository.RegionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private KommuneRepository kommuneRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Region> regions = fetchRegions();

        for (Region region : regions) {
            regionRepository.save(region);
            System.out.println("Region: " + region);
        }

        Kommune fakeKommune = new Kommune("1234", "Klovnekommune", "http://hej.dk");
        Region inficeretRegion = regions.get(3);
        fakeKommune.setRegion(inficeretRegion);

        kommuneRepository.save(fakeKommune);
        // regionRepository.save(inficeretRegion);
    }

    public static List<Region> fetchRegions() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("https://api.dataforsyningen.dk/regioner"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        List<Region> regions = new ArrayList<>();
        for (JsonNode node : root) {
            String kode = node.get("kode").asText();
            String navn = node.get("navn").asText();
            String href = node.get("href").asText();

            regions.add(new Region(kode, navn, href));
        }
        return regions;
    }

}
