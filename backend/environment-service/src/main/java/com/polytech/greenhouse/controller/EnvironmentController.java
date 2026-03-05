package com.polytech.greenhouse.controller;

import com.polytech.greenhouse.entity.Measurement;
import com.polytech.greenhouse.entity.Parameter;
import com.polytech.greenhouse.enums.EnvType;
import com.polytech.greenhouse.service.MeasurementService;
import com.polytech.greenhouse.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/environment")
@RequiredArgsConstructor
public class EnvironmentController {

    private final MeasurementService measurementService;
    private final ParameterService parameterService;

    // --- Measurement Endpoints ---

    @PostMapping("/measurements")
    public ResponseEntity<Measurement> addMeasurement(@RequestBody Measurement measurement) {
        return ResponseEntity.ok(measurementService.recordMeasurement(measurement));
    }

    @GetMapping("/measurements")
    public ResponseEntity<List<Measurement>> getAllMeasurements() {
        return ResponseEntity.ok(measurementService.getAllMeasurements());
    }

    // --- Parameter Endpoints ---

    @PostMapping("/parameters")
    public ResponseEntity<Parameter> configureParameter(@RequestBody Parameter parameter) {
        return ResponseEntity.ok(parameterService.createOrUpdateParameter(parameter));
    }

    @GetMapping("/parameters/{type}")
    public ResponseEntity<Parameter> getParameter(@PathVariable EnvType type) {
        return ResponseEntity.ok(parameterService.getParameterByType(type));
    }
}