package com.polytech.greenhouse.service;

import com.polytech.greenhouse.dto.MeasurementDTO;
import com.polytech.greenhouse.entity.Measurement;
import java.util.List;

public interface MeasurementService {
    Measurement recordMeasurement(MeasurementDTO measurementDTO);
    List<Measurement> getAllMeasurements();
}