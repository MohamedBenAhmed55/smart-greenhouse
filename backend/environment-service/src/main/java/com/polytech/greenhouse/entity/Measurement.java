package com.polytech.greenhouse.entity;

import com.polytech.greenhouse.enums.EnvType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnvType type;

    private Double value;
    private LocalDateTime timestamp;

    // Link to the configuration (Parameter)
    private Long parameterId;
}