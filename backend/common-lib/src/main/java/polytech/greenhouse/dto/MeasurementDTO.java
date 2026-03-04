package com.polytech.greenhouse.dto;

import com.polytech.greenhouse.enums.EnvType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementDTO {
    private Long id;
    private EnvType type;
    private Double value;
    private LocalDateTime timestamp;
    private Long sensorId;
}