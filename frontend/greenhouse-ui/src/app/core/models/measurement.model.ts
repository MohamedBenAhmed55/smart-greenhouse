export interface Measurement {
  id?: number;
  type: 'TEMPERATURE' | 'HUMIDITY' | 'LUMINOSITY' | 'SOIL_MOISTURE';
  value: number;
  timestamp: string; // ISO String from Java
  sensorId: number;
}
