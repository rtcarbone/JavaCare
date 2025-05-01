package br.com.fiap.javacare.scheduling.graphql;

import br.com.fiap.javacare.scheduling.dto.AppointmentInputDTO;
import br.com.fiap.javacare.scheduling.mapper.AppointmentMapper;
import br.com.fiap.javacare.scheduling.model.Appointment;
import br.com.fiap.javacare.scheduling.repository.AppointmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    public AppointmentMutationResolver(AppointmentRepository repository, RabbitTemplate rabbitTemplate, AppointmentMapper mapper, ObjectMapper objectMapper) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @MutationMapping
    public Appointment createAppointment(@Argument AppointmentInputDTO input) {
        Appointment appointment = mapper.toEntity(input);
        appointment.setId(UUID.randomUUID());

        Appointment saved = repository.save(appointment);

        try {
            String json = objectMapper.writeValueAsString(saved);
            rabbitTemplate.convertAndSend(QUEUE_NAME, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar a mensagem", e);
        }

        return saved;
    }

}
