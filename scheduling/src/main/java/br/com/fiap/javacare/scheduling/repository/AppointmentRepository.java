package br.com.fiap.javacare.scheduling.repository;

import br.com.fiap.javacare.scheduling.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
}