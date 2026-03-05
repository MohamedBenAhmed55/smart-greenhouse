package com.polytech.greenhouse.mapper;

import com.polytech.greenhouse.dto.MeasurementDTO;
import com.polytech.greenhouse.entity.Measurement;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapper {

    public Measurement toEntity(MeasurementDTO dto) {
        return Measurement.builder()
                .type(dto.getType())
                .value(dto.getValue())
                .timestamp(dto.getTimestamp())
                .parameterId(dto.getSensorId())
                .build();
    }

    public MeasurementDTO toDto(Measurement entity) {
        return MeasurementDTO.builder()
                .id(entity.getId())
                .type(entity.getType())
                .value(entity.getValue())
                .timestamp(entity.getTimestamp())
                .sensorId(entity.getParameterId())
                .build();
    }
}