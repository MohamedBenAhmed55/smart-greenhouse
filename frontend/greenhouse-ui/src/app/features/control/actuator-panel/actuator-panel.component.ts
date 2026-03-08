import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {GreenhouseStore} from '../../../state/greenhouse.store';
import {ApiService} from '../../../core/api/api.service';
import {Equipment} from '../../../core/models/equipment.model'; // Import Forms Module

@Component({
  selector: 'app-actuator-panel',
  standalone: true,
  imports: [CommonModule, FormsModule], // Add FormsModule here
  templateUrl: './actuator-panel.component.html',
  styleUrls: ['./actuator-panel.component.scss']
})
export class ActuatorPanelComponent implements OnInit {
  store = inject(GreenhouseStore);
  api = inject(ApiService);

  loadingActionId: number | null = null;

  // Form Model
  newEquipment: Partial<Equipment> = {
    name: '',
    type: 'FAN',
    active: false
  };

  showForm = false; // Toggle for the form

  ngOnInit() {
    this.api.fetchEquipmentStatus();
    this.api.fetchActionHistory();
  }

  async toggleActuator(equipment: Equipment) {
    this.loadingActionId = equipment.id;
    const newAction = equipment.active ? 'STOP' : 'START';

    try {
      // 1. Send the command
      await this.api.sendCommand(equipment.type, newAction);

      // 2. [FIX] Wait briefly, then REFRESH the list to update the UI
      setTimeout(() => {
        this.api.fetchEquipmentStatus(); // Updates the 'Running/Stopped' badge
        this.api.fetchActionHistory();   // Updates the logs
      }, 500);

    } catch (err) {
      console.error('Toggle failed', err);
    } finally {
      this.loadingActionId = null;
    }
  }

  async createEquipment() {
    if (!this.newEquipment.name) return;

    try {
      await this.api.addEquipment(this.newEquipment);
      this.showForm = false; // Close form
      this.newEquipment.name = ''; // Reset form
    } catch (err) {
      alert('Failed to add equipment');
    }
  }

  getIcon(type: string | undefined): string {
    switch (type) {
      case 'FAN': return '💨';
      case 'HEATER': return '🔥';
      case 'WATER_PUMP': return '💧';
      case 'LIGHT_SYSTEM': return '💡';
      case 'WINDOW_MOTOR': return '🪟';
      default: return '⚙️';
    }
  }
}
