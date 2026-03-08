import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'unitFormat',
  standalone: true
})
export class UnitFormatPipe implements PipeTransform {
  transform(value: number | undefined, type: string | undefined): string {
    if (value === undefined || value === null) return '--';

    switch (type) {
      case 'TEMPERATURE': return `${value.toFixed(1)} °C`;
      case 'HUMIDITY': return `${value.toFixed(0)} %`;
      case 'LUMINOSITY': return `${value.toFixed(0)} Lux`;
      case 'SOIL_MOISTURE': return `${value.toFixed(0)} %`;
      default: return `${value}`;
    }
  }
}
