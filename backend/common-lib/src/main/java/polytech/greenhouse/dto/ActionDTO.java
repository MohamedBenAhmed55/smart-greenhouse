package com.polytech.greenhouse.dto;

import com.polytech.greenhouse.enums.ActuatorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionDTO {
    private Long id;
    private Long actuatorId;
    private ActuatorType type;
    private String action;
    private String reason;
    private LocalDateTime executedAt;
}