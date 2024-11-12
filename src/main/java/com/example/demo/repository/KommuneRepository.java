package com.example.demo.repository;

import com.example.demo.model.Kommune;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KommuneRepository extends JpaRepository<Kommune, Integer> {
}
