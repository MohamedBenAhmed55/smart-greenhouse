export interface Equipment {
  id: number;
  name: string;
  type: 'FAN' | 'HEATER' | 'WATER_PUMP' | 'LIGHT_SYSTEM' | 'WINDOW_MOTOR';
  active: boolean; // true = ON, false = OFF
}
