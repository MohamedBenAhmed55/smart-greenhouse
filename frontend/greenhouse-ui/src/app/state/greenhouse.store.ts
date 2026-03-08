import { Injectable, signal, computed } from '@angular/core';
import { Measurement } from '../core/models/measurement.model';
import { Equipment } from '../core/models/equipment.model';
import { Alert } from '../core/models/alert.model';
import { ActionLog } from '../core/models/action-log.model';

@Injectable({
  providedIn: 'root'
})
export class GreenhouseStore {
  readonly measurements = signal<Measurement[]>([]);
  readonly equipments = signal<Equipment[]>([]);
  readonly alerts = signal<Alert[]>([]);

  readonly isLoading = signal<boolean>(false);
  readonly connectionStatus = signal<'CONNECTED' | 'DISCONNECTED' | 'ERROR'>('DISCONNECTED');


  // Get the most recent value for Temperature
  readonly latestTemp = computed<Measurement | undefined>(() => {
    const temps = this.measurements().filter(m => m.type === 'TEMPERATURE');
    return temps.sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())[0];
  });

  // Get the most recent value for Humidity
  readonly latestHumidity = computed<Measurement | undefined>(() => {
    const hums = this.measurements().filter(m => m.type === 'HUMIDITY');
    return hums.sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())[0];
  });

  // Count unread alerts
  readonly unreadAlertCount = computed(() =>
    this.alerts().filter(a => !a.read).length
  );

  // --- ACTIONS (Methods to update state) ---

  setMeasurements(data: Measurement[]) {
    this.measurements.set(data);
  }

  setEquipments(data: Equipment[]) {
    this.equipments.set(data);
  }

  setLoading(loading: boolean) {
    this.isLoading.set(loading);
  }

  setConnectionStatus(status: 'CONNECTED' | 'DISCONNECTED' | 'ERROR') {
    this.connectionStatus.set(status);
  }

  addAlert(message: string) {
    const newAlert: Alert = {
      id: crypto.randomUUID(), // Generate a unique ID
      message: message,
      timestamp: new Date(),
      read: false
    };
    // Add to the START of the array
    this.alerts.update(current => [newAlert, ...current]);
  }

  markAlertAsRead(id: string) {
    this.alerts.update(list =>
      list.map(a => a.id === id ? { ...a, read: true } : a)
    );
  }

  readonly actionHistory = signal<ActionLog[]>([]);

  setActionHistory(data: ActionLog[]) {
    this.actionHistory.set(data);
  }
}
