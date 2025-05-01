package br.com.fiap.javacare.notification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AppointmentNotificationListener {

    @RabbitListener(queues = "appointment-notification-queue")
    public void onMessage(String payload) {
        System.out.println("[📩 Notification] Message received from queue:");
        System.out.println(payload);
        // Aqui você poderia deserializar e simular envio de e-mail/SMS
    }
}
