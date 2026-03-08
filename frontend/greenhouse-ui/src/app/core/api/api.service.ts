import { Injectable, inject } from '@angular/core';
import axios, { AxiosInstance } from 'axios';
import { GreenhouseStore } from '../../state/greenhouse.store';
import { Measurement } from '../models/measurement.model'; // We will create this model shortly

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private store = inject(GreenhouseStore);

  // Create a configured Axios instance
  private client: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api/v1', // Points to Java Gateway
    timeout: 5000,
    headers: {
      'Content-Type': 'application/json'
    }
  });

  /**
   * Fetch latest sensor data from Environment Service
   */
  async fetchMeasurements(): Promise<void> {
    this.store.setLoading(true);
    try {
      const response = await this.client.get<Measurement[]>('/environment/measurements');
      this.store.setMeasurements(response.data);
    } catch (error) {
      console.error('API Error (Measurements):', error);
      // In a real app, you'd update an error signal in the store here
    } finally {
      this.store.setLoading(false);
    }
  }

  /**
   * Send a command to the Control Service (e.g., Turn Fan ON)
   */
  async sendCommand(actuatorType: string, action: 'START' | 'STOP'): Promise<void> {
    try {
      // Matches your ControlController endpoint logic
      await this.client.post('/control/equipments/action', {
        type: actuatorType,
        action: action
      });
      // Optionally refresh equipment status after command
      await this.fetchEquipmentStatus();
    } catch (error) {
      console.error('API Error (Control):', error);
    }
  }

  /**
   * Fetch current status of all actuators
   */
  async fetchEquipmentStatus(): Promise<void> {
    try {
      const response = await this.client.get('/control/equipments');
      // We will add a setEquipments method to the store later
      this.store.setEquipments(response.data);
    } catch (error) {
      console.error('API Error (Equipment):', error);
    }
  }

  /**
   * Add new equipment to the system
   */
  async addEquipment(equipment: any): Promise<void> {
    try {
      await this.client.post('/control/equipments', equipment);
      // Refresh list immediately
      await this.fetchEquipmentStatus();
    } catch (error) {
      console.error('API Error (Add Equipment):', error);
      throw error; // Re-throw so component can show error
    }
  }

  /**
   * Get history of actions
   */
  async fetchActionHistory(): Promise<void> {
    try {
      const response = await this.client.get('/control/actions');
      this.store.setActionHistory(response.data);
    } catch (error) {
      console.error('API Error (History):', error);
    }
  }

  async sendMeasurement(type: string, value: number): Promise<void> {
    const payload = {
      type: type,
      value: value,
      timestamp: new Date().toISOString(),
      sensorId: 1 // ID 999 indicates manual simulation
    };

    try {
      await this.client.post('/environment/measurements', payload);
      this.fetchMeasurements(); // Refresh UI immediately
    } catch (error) {
      console.error('Simulation Failed:', error);
      throw error;
    }
  }
}
