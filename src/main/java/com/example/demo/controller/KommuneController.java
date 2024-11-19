package com.example.demo.controller;

import com.example.demo.model.Kommune;
import com.example.demo.model.Region;
import com.example.demo.repository.KommuneRepository;
import com.example.demo.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
