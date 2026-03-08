import {Component, inject} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {GreenhouseStore} from './state/greenhouse.store';
import {WebsocketService} from './core/services/websocket.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'greenhouse-ui';
  ws = inject(WebsocketService);
  store = inject(GreenhouseStore);

  ngOnInit() {
    // Start WebSocket connection when app loads
    this.ws.connect();
  }
}
