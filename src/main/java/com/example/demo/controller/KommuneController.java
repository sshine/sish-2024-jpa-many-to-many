package com.example.demo.controller;

import com.example.demo.model.Kommune;
import com.example.demo.repository.KommuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KommuneController {

    private KommuneRepository kommuneRepository;

    public KommuneController(KommuneRepository kommuneRepository) {
        this.kommuneRepository = kommuneRepository;
    }

    @GetMapping("/kommuner")
    public List<Kommune> getByRegion(@RequestParam String regionskode) {
        return kommuneRepository.findByRegion_Kode(regionskode);
    }
}
