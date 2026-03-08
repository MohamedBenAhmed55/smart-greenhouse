package com.polytech.greenhouse.listener;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AlertListener {

    // Listen to ALL alerts (alert.*)
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "alert_notification_queue", durable = "true"),
            exchange = @Exchange(value = "greenhouse-exchange", type = "topic", ignoreDeclarationExceptions = "true"),
            key = "alert.#"
    ))
    public void handleAlert(String message) {
        System.out.println("========================================");
        System.out.println("🚨 RECEIVED CRITICAL ALERT: " + message);
        System.out.println("📧 Sending notification to admin...");
        System.out.println("========================================");
    }
}