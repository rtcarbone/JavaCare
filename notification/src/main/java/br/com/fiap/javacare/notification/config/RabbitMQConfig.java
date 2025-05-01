package br.com.fiap.javacare.notification.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "appointment-notification-queue";

    @Bean
    public Queue appointmentNotificationQueue() {
        return new Queue(QUEUE_NAME, true); // durable=true
    }
}