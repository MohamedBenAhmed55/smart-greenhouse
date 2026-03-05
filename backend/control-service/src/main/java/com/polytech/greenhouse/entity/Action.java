package com.polytech.greenhouse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "equipment_action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long equipmentId;

    // The parameter that triggered this (e.g., The ID of the Temperature Config)
    private Long parameterId;

    private String actionType; // "START", "STOP"

    @CreationTimestamp
    private LocalDateTime executionDate;
}