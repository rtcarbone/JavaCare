package br.com.fiap.javacare.scheduling.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentNotificationDTO(
        UUID id,
        UUID patientId,
        UUID doctorId,
        LocalDateTime appointmentDateTime,
        String status
) implements Serializable {
}
