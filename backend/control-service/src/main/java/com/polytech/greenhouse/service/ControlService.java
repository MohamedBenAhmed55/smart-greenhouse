package com.polytech.greenhouse.service;

import com.polytech.greenhouse.dto.ActionDTO;
import com.polytech.greenhouse.entity.Equipment;
import com.polytech.greenhouse.enums.ActuatorType;
import java.util.List;

public interface ControlService {

    // Core Logic: React to alerts
    void executeAutomaticAction(ActuatorType type, boolean turnOn, String reason);

    // Management: Add/View devices
    Equipment registerEquipment(Equipment equipment);
    List<Equipment> getAllEquipments();

    // View History (Returns DTOs)
    List<ActionDTO> getActionHistory();
}