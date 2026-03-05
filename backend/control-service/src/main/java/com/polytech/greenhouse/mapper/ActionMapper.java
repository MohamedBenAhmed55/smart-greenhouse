package com.polytech.greenhouse.mapper;

import com.polytech.greenhouse.dto.ActionDTO;
import com.polytech.greenhouse.entity.Action;
import com.polytech.greenhouse.entity.Equipment;
import com.polytech.greenhouse.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionMapper {

    private final EquipmentRepository equipmentRepository;

    public ActionDTO toDto(Action entity) {
        Equipment equipment = equipmentRepository.findById(entity.getEquipmentId()).orElse(null);

        return ActionDTO.builder()
                .id(entity.getId())
                .actuatorId(entity.getEquipmentId())
                .type(equipment != null ? equipment.getType() : null)
                .action(entity.getActionType())
                .reason("Automatic Control")
                .executedAt(entity.getExecutionDate())
                .build();
    }
}