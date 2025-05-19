package br.com.fiap.javacare.notification.listener;

import br.com.fiap.javacare.notification.dto.AppointmentNotificationDTO;
import br.com.fiap.javacare.notification.service.EmailService;
import br.com.fiap.javacare.notification.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppointmentNotificationListener {

    private final EmailService emailService;
    private final UserServiceClient userServiceClient;

    @RabbitListener(queues = "appointment-notification-queue")
    public void receive(AppointmentNotificationDTO dto) {
        log.info("Notificação recebida: {}", dto);

        var patient = userServiceClient.getUserById(dto.patientId());
        var doctor = userServiceClient.getUserById(dto.doctorId());

        String html = """
                <html>
                <body>
                    <p>Olá <strong>%s</strong>,</p>
                    <p>Sua consulta foi atualizada com as seguintes informações:</p>
                
                    <ul>
                        <li><strong>Médico(a):</strong> %s</li>
                        <li><strong>Data/Hora:</strong> %s</li>
                        <li><strong>Status:</strong> %s</li>
                    </ul>
                
                    <p>Atenciosamente,<br/>
                    <em>Equipe JavaCare</em></p>
                </body>
                </html>
                """.formatted(
                patient.fullName(),
                doctor.fullName(),
                dto.appointmentDateTime(),
                dto.status()
        );

        emailService.sendHtml(patient.email(), "Atualização da Consulta", html);
    }
}
