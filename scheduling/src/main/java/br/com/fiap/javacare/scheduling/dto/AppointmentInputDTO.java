package br.com.fiap.javacare.scheduling.dto;

import java.io.Serializable;
import java.util.UUID;

public record AppointmentInputDTO(
        UUID id,
        UUID patientId,
        UUID doctorId,
        String appointmentDateTime,
        String status,
        UUID userId
) implements Serializable {
}
