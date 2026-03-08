import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {Measurement} from '../../../core/models/measurement.model';
import {ApiService} from '../../../core/api/api.service';
import {GreenhouseStore} from '../../../state/greenhouse.store';
import {UnitFormatPipe} from '../../../shared/pipes/unit-format.pipe';

@Component({
  selector: 'app-sensor-dashboard',
  standalone: true,
  imports: [CommonModule, UnitFormatPipe, FormsModule],
  templateUrl: './sensor-dashboard.component.html',
  styleUrls: ['./sensor-dashboard.component.scss']
})
export class SensorDashboardComponent implements OnInit, OnDestroy {
  store = inject(GreenhouseStore);
  api = inject(ApiService);
  private intervalId: any;

  // [NEW] Simulation Vars
  simType: string = 'TEMPERATURE';
  simValue: number = 25;
  isSimulating = false;

  ngOnInit() {
    this.loadData();
    this.intervalId = setInterval(() => this.loadData(), 10000);
  }

  ngOnDestroy() {
    if (this.intervalId) clearInterval(this.intervalId);
  }

  loadData() {
    this.api.fetchMeasurements();
  }

  getLatestValue(type: string): Measurement | undefined {
    return this.store.measurements()
      .filter(m => m.type === type)
      .sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())[0];
  }

  // [NEW] Send Simulation
  async onSimulate() {
    this.isSimulating = true;
    try {
      await this.api.sendMeasurement(this.simType, this.simValue);
    } catch (err) {
      alert('Failed to send measurement');
    } finally {
      this.isSimulating = false;
    }
  }
}
