package com.polytech.greenhouse.service.impl;

import com.polytech.greenhouse.dto.ActionDTO;
import com.polytech.greenhouse.entity.Action;
import com.polytech.greenhouse.entity.Equipment;
import com.polytech.greenhouse.enums.ActuatorType;
import com.polytech.greenhouse.mapper.ActionMapper;
import com.polytech.greenhouse.repository.ActionRepository;
import com.polytech.greenhouse.repository.EquipmentRepository;
import com.polytech.greenhouse.service.ControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ControlServiceImpl implements ControlService {

    private final EquipmentRepository equipmentRepository;
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;

    @Override
    @Transactional
    public void executeAutomaticAction(ActuatorType type, boolean turnOn, String reason) {
        List<Equipment> equipments = equipmentRepository.findByType(type);

        if (equipments.isEmpty()) {
            log.warn("⚠️ ALERT RECEIVED but no equipment found for type: {}", type);
            return;
        }

        for (Equipment eq : equipments) {
            // Only act if the state is changing
            if (eq.getActive() == null || eq.getActive() != turnOn) {

                // 1. Update Equipment State
                eq.setActive(turnOn);
                equipmentRepository.save(eq);

                // 2. Create Action Entity (Internal Record)
                Action action = Action.builder()
                        .equipmentId(eq.getId())
                        .actionType(turnOn ? "START" : "STOP")
                        .build();

                actionRepository.save(action);

                log.info(" ✅ [ACTION] {} switched {} due to: {}", eq.getName(), (turnOn ? "ON" : "OFF"), reason);
            } else {
                log.info(" ℹ️ [NO-OP] {} is already {}", eq.getName(), (turnOn ? "ON" : "OFF"));
            }
        }
    }

    @Override
    public Equipment registerEquipment(Equipment equipment) {
        if (equipment.getActive() == null) {
            equipment.setActive(false);
        }
        return equipmentRepository.save(equipment);
    }

    @Override
    public List<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }

    /**
     * Retrieves all actions and converts them to DTOs using the Mapper.
     * This ensures the API receives clean data (ActionDTO) instead of raw Entities.
     */
    @Override
    public List<ActionDTO> getActionHistory() {
        return actionRepository.findAll()
                .stream()
                .map(actionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void sendCommand(String typeStr, String actionStr) {
        try {
            // 1. Convert String "FAN" -> Enum ActuatorType.FAN
            ActuatorType type = ActuatorType.valueOf(typeStr);

            // 2. Convert String "START"/"STOP" -> boolean true/false
            boolean turnOn = "START".equalsIgnoreCase(actionStr);

            // 3. Reuse your existing logic!
            // We pass "Manual User Override" as the reason so it shows up in logs clearly.
            executeAutomaticAction(type, turnOn, "Manual User Override");

        } catch (IllegalArgumentException e) {
            log.error("❌ Invalid manual command received: Type={}, Action={}", typeStr, actionStr);
            throw new RuntimeException("Invalid equipment type or action provided.");
        }
    }
}