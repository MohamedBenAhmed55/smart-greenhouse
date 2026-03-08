package com.polytech.greenhouse.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertListener {

    private final SimpMessagingTemplate messagingTemplate;

    // Listen to ALL alerts (alert.*)
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "alert_notification_queue", durable = "true"),
            exchange = @Exchange(value = "greenhouse-exchange", type = "topic", ignoreDeclarationExceptions = "true"),
            key = "alert.#"
    ))
    public void handleAlert(String message) {
        log.info("========================================");
        log.info("🚨 RECEIVED CRITICAL ALERT: {}", message);
        log.info("📧 Sending notification to admin...");
        log.info("========================================");

        messagingTemplate.convertAndSend("/topic/alerts", message);
        log.info("📡 Sent to WebSocket: /topic/alerts");
    }
}