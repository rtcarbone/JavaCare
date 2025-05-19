package br.com.fiap.javacare.history.graphql;

import br.com.fiap.javacare.history.model.ActionType;
import br.com.fiap.javacare.history.model.Appointment;
import br.com.fiap.javacare.history.model.AppointmentStatus;
import br.com.fiap.javacare.history.repository.AppointmentRepository;
import br.com.fiap.javacare.history.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AppointmentQueryResolver {

    private final AppointmentRepository repository;
    private final UserServiceClient userServiceClient;

    @QueryMapping
    public List<Appointment> appointments(@Argument UUID userId,
                                          @Argument UUID patientId,
                                          @Argument UUID doctorId,
                                          @Argument AppointmentStatus status) {

        boolean allowed = userServiceClient.canAccess(
                userId,
                patientId,
                ActionType.VIEW_HISTORY
        );

        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        return repository.findAll()
                .stream()
                .filter(appointment -> patientId == null || appointment.getPatientId()
                        .equals(patientId))
                .filter(appointment -> doctorId == null || appointment.getDoctorId()
                        .equals(doctorId))
                .filter(appointment -> status == null || appointment.getStatus()
                        .equals(status))
                .toList();
    }

    @QueryMapping
    public Appointment getAppointmentById(@Argument UUID id,
                                          @Argument UUID userId) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

        boolean allowed = userServiceClient.canAccess(
                userId,
                appointment.getPatientId(),
                ActionType.VIEW_HISTORY
        );

        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        return appointment;
    }
}
