import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // needed for DatePipe
import { GreenhouseStore } from '../../../state/greenhouse.store';
import { WebsocketService } from '../../../core/services/websocket.service';

@Component({
  selector: 'app-alert-feed',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './alert-feed.component.html',
  styleUrls: ['./alert-feed.component.scss']
})
export class AlertFeedComponent implements OnInit {
  store = inject(GreenhouseStore);
  ws = inject(WebsocketService);

  ngOnInit() {
    // Ensure connection is active when visiting this page
    this.ws.connect();
  }

  markAsRead(id: string) {
    this.store.markAlertAsRead(id);
  }
}
