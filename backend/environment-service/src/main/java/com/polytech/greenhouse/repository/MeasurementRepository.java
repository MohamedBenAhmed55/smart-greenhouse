package com.polytech.greenhouse.repository;

import com.polytech.greenhouse.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
}