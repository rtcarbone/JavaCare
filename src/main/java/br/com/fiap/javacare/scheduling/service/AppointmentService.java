package br.com.fiap.javacare.scheduling.service;

import br.com.fiap.javacare.scheduling.model.Appointment;
import br.com.fiap.javacare.scheduling.repository.AppointmentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static br.com.fiap.javacare.config.RabbitMQConfig.QUEUE_NAME;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final RabbitTemplate rabbitTemplate;

    public AppointmentService(AppointmentRepository appointmentRepository, RabbitTemplate rabbitTemplate) {
        this.appointmentRepository = appointmentRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Appointment scheduleAppointment(Appointment appointment) {
        Appointment newAppointment = appointmentRepository.save(appointment);
        rabbitTemplate.convertAndSend(QUEUE_NAME, newAppointment);
        return newAppointment;
    }

}
