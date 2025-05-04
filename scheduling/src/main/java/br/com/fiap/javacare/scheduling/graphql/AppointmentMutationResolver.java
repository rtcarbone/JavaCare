package br.com.fiap.javacare.scheduling.graphql;

import br.com.fiap.javacare.scheduling.dto.AppointmentInputDTO;
import br.com.fiap.javacare.scheduling.dto.AppointmentNotificationDTO;
import br.com.fiap.javacare.scheduling.mapper.AppointmentMapper;
import br.com.fiap.javacare.scheduling.model.Appointment;
import br.com.fiap.javacare.scheduling.model.AppointmentStatus;
import br.com.fiap.javacare.scheduling.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class AppointmentMutationResolver {

    private static final String QUEUE_NAME = "appointment-notification-queue";
    private final AppointmentRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final AppointmentMapper mapper;

    public AppointmentMutationResolver(AppointmentRepository repository, RabbitTemplate rabbitTemplate, AppointmentMapper mapper) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
    }

    @MutationMapping
    public Appointment createAppointment(@Argument AppointmentInputDTO input) {
        Appointment appointment = mapper.toEntity(input);
        appointment.setId(UUID.randomUUID());

        Appointment saved = repository.save(appointment);

        AppointmentNotificationDTO message = mapper.toNotificationDto(saved);
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);

        return saved;
    }

    @MutationMapping
    public Appointment updateAppointment(@Argument AppointmentInputDTO input) {
        UUID id = input.id();
        Appointment existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Appointment updated = mapper.toEntity(input);
        updated.setId(existing.getId());

        updated = repository.save(updated);

        AppointmentNotificationDTO message = mapper.toNotificationDto(updated);
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);

        return updated;
    }

    @MutationMapping
    public Appointment cancelAppointment(@Argument UUID id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found: " + id));

        appointment.setStatus(AppointmentStatus.CANCELLED);

        Appointment canceled = repository.save(appointment);

        AppointmentNotificationDTO message = mapper.toNotificationDto(appointment);
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);

        return canceled;
    }

}
