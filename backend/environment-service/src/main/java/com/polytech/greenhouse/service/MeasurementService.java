package com.polytech.greenhouse.service;

import com.polytech.greenhouse.entity.Measurement;
import java.util.List;

public interface MeasurementService {
    Measurement recordMeasurement(Measurement measurement);
    List<Measurement> getAllMeasurements();
}