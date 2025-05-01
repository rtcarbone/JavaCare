package br.com.fiap.javacare.scheduling.mapper;

import br.com.fiap.javacare.scheduling.dto.AppointmentInputDTO;
import br.com.fiap.javacare.scheduling.dto.AppointmentNotificationDTO;
import br.com.fiap.javacare.scheduling.model.Appointment;
import br.com.fiap.javacare.scheduling.model.AppointmentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Named("toLocalDateTime")
    static LocalDateTime toLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime);
    }

    @Named("toStatusEnum")
    static AppointmentStatus toStatusEnum(String status) {
        return AppointmentStatus.valueOf(status.toUpperCase());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointmentDateTime", source = "appointmentDateTime", qualifiedByName = "toLocalDateTime")
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatusEnum")
    Appointment toEntity(AppointmentInputDTO dto);

    AppointmentNotificationDTO toNotificationDto(Appointment appointment);

}

