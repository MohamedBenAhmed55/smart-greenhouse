package com.polytech.greenhouse.entity;

import com.polytech.greenhouse.enums.ActuatorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActuatorType type; // e.g., FAN, HEATER

    private Boolean active; // true = ON, false = OFF

    private String name; // e.g., "Main Fan", "Backup Pump"
}