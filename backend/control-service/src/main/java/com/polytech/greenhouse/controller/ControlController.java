package com.polytech.greenhouse.controller;

import com.polytech.greenhouse.dto.ActionDTO;
import com.polytech.greenhouse.entity.Equipment;
import com.polytech.greenhouse.service.ControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/control")
@RequiredArgsConstructor
public class ControlController {

    private final ControlService controlService;

    // --- Equipment Management ---

    @PostMapping("/equipments")
    public ResponseEntity<Equipment> addEquipment(@RequestBody Equipment equipment) {
        return ResponseEntity.ok(controlService.registerEquipment(equipment));
    }

    @GetMapping("/equipments")
    public ResponseEntity<List<Equipment>> getAllEquipments() {
        return ResponseEntity.ok(controlService.getAllEquipments());
    }

    // --- History / Logs ---

    @GetMapping("/actions")
    public ResponseEntity<List<ActionDTO>> getActionHistory() {
        return ResponseEntity.ok(controlService.getActionHistory());
    }
}