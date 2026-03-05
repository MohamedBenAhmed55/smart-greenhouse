package com.polytech.greenhouse.service.impl;

import com.polytech.greenhouse.entity.Measurement;
import com.polytech.greenhouse.entity.Parameter;
import com.polytech.greenhouse.repository.MeasurementRepository;
import com.polytech.greenhouse.repository.ParameterRepository;
import com.polytech.greenhouse.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final ParameterRepository parameterRepository;

    @Override
    public Measurement recordMeasurement(Measurement measurement) {
        // 1. Auto-set timestamp
        measurement.setTimestamp(LocalDateTime.now());

        // 2. Link to Parameter ID based on the EnvType
        // We try to find if a configuration exists for this type (e.g. TEMPERATURE)
        Optional<Parameter> param = parameterRepository.findByType(measurement.getType());

        // If found, we link it using the ID field as defined in your Entity
        param.ifPresent(p -> measurement.setParameterId(p.getId()));

        // 3. Save
        Measurement saved = measurementRepository.save(measurement);

        // TODO: (Future Step) Check if value exceeds min/max thresholds and send RabbitMQ alert

        return saved;
    }

    @Override
    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }
}