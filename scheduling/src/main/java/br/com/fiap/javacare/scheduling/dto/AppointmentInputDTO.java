package br.com.fiap.javacare.scheduling.dto;

import java.util.UUID;

public record AppointmentInputDTO(
        UUID id,
        UUID patientId,
        UUID doctorId,
        String appointmentDateTime,
        String status
) {
}
