package br.com.fiap.javacare.notification.listener;

import br.com.fiap.javacare.notification.dto.AppointmentNotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppointmentNotificationListener {

    @RabbitListener(queues = "appointment-notification-queue")
    public void receive(AppointmentNotificationDTO dto) {
        log.info("Notificação recebida: {}", dto);
    }

}
