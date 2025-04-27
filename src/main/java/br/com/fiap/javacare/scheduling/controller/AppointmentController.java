package br.com.fiap.javacare.scheduling.controller;

import br.com.fiap.javacare.scheduling.model.Appointment;
import br.com.fiap.javacare.scheduling.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> scheduleAppointment(@RequestBody Appointment appointment) {
        Appointment newAppointment = appointmentService.scheduleAppointment(appointment);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }
}
