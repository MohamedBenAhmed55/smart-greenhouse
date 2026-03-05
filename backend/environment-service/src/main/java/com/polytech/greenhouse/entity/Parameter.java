package com.polytech.greenhouse.entity;

import com.polytech.greenhouse.enums.EnvType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnvType type; // TEMPERATURE, HUMIDITY...

    private Double minThreshold;
    private Double maxThreshold;
}