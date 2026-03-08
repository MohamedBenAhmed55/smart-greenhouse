export interface ActionLog {
  id?: number;
  equipmentId: number;
  type: string;   // e.g., "FAN", "HEATER"
  action: string; // "START", "STOP"
  timestamp: string;
}
