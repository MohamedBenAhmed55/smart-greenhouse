import { Injectable, inject } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { GreenhouseStore } from '../../state/greenhouse.store';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private store = inject(GreenhouseStore);
  private client: Client | null = null;

  // Pointing directly to Alert Service (bypass gateway for now to avoid config complexity)
  private readonly WS_URL = 'http://localhost:8083/ws-alerts';

  connect() {
    // Prevent multiple connections
    if (this.client && this.client.active) return;

    try {
      const socket = new SockJS(this.WS_URL);

      this.client = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,
        debug: (str) => console.log('[WS Debug]:', str),

        onConnect: () => {
          console.log('✅ WebSocket Connected');
          this.store.setConnectionStatus('CONNECTED');

          this.client?.subscribe('/topic/alerts', (message: Message) => {
            if (message.body) {
              this.store.addAlert(message.body);
            }
          });
        },

        onStompError: (frame) => {
          console.error('❌ Broker reported error: ' + frame.headers['message']);
          this.store.setConnectionStatus('ERROR');
        },

        onWebSocketClose: () => {
          console.warn('⚠️ WebSocket connection closed');
          this.store.setConnectionStatus('DISCONNECTED');
        }
      });

      this.client.activate();

    } catch (error) {
      console.error('CRITICAL WS ERROR:', error);
      this.store.setConnectionStatus('ERROR');
    }
  }

  disconnect() {
    if (this.client) {
      this.client.deactivate();
    }
  }
}
