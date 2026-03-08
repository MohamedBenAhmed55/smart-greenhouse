import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'environment',
    pathMatch: 'full'
  },
  {
    path: 'environment',
    // Lazy load the sensor dashboard
    loadComponent: () =>
      import('./features/environment/sensor-dashboard/sensor-dashboard.component')
        .then(m => m.SensorDashboardComponent)
  },
  {
    path: 'control',
    // Lazy load the control panel
    loadComponent: () =>
      import('./features/control/actuator-panel/actuator-panel.component')
        .then(m => m.ActuatorPanelComponent)
  },
  {
    path: 'alerts',
    // Lazy load the alert feed
    loadComponent: () =>
      import('./features/alerts/alert-feed/alert-feed.component')
        .then(m => m.AlertFeedComponent)
  },
  {
    // Catch-all for 404s
    path: '**',
    redirectTo: 'environment'
  }
];
