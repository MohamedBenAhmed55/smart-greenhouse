package com.polytech.greenhouse.repository;

import com.polytech.greenhouse.entity.Equipment;
import com.polytech.greenhouse.enums.ActuatorType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    // Find all actuators of a specific type (e.g., Find all FANS)
    List<Equipment> findByType(ActuatorType type);
}