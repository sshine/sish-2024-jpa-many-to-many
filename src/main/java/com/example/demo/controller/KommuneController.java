package com.example.demo.controller;

import com.example.demo.model.Kommune;
import com.example.demo.model.Region;
import com.example.demo.repository.KommuneRepository;
import com.example.demo.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class KommuneController {
    private RegionRepository regionRepository;
    private KommuneRepository kommuneRepository;

    public KommuneController(RegionRepository regionRepository, KommuneRepository kommuneRepository) {
        this.regionRepository = regionRepository;
        this.kommuneRepository = kommuneRepository;
    }

    @GetMapping("/regioner")
    public List<Region> getRegions() {
        return regionRepository.findAll();
    }

    @GetMapping("/kommuner")
    public List<Kommune> getByRegion(@RequestParam(required = false) String regionskode) {
        return regionskode == null
            ? kommuneRepository.findAll()
            : kommuneRepository.findByRegion_Kode(regionskode);
    }

    @PostMapping("/kommuner/remove")
    public void removeKommune(@RequestParam int id) {
        Optional<Kommune> kommune = kommuneRepository.findById(id);
        if (kommune.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kommune ikke fundet");
        }
        kommuneRepository.delete(kommune.get());
    }
}
