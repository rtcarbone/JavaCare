package br.com.fiap.javacare.history.graphql;

import br.com.fiap.javacare.history.model.Appointment;
import br.com.fiap.javacare.history.model.AppointmentStatus;
import br.com.fiap.javacare.history.repository.AppointmentRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Controller
public class AppointmentQueryResolver {

    private final AppointmentRepository repository;

    public AppointmentQueryResolver(AppointmentRepository repository) {
        this.repository = repository;
    }

    @QueryMapping
    public List<Appointment> appointments(@Argument UUID patientId,
                                          @Argument UUID doctorId,
                                          @Argument AppointmentStatus status) {
        return repository.findAll()
                .stream()
                .filter(a -> patientId == null || a.getPatientId()
                        .equals(patientId))
                .filter(a -> doctorId == null || a.getDoctorId()
                        .equals(doctorId))
                .filter(a -> status == null || a.getStatus()
                        .equals(status))
                .toList();
    }

    @QueryMapping
    public Appointment getAppointmentById(@Argument UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));
    }

}
