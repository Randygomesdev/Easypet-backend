package br.com.easypet.dto.response;

import br.com.easypet.domain.entity.Appointment;
import br.com.easypet.domain.enums.AppointmentStatus;
import br.com.easypet.domain.enums.AppointmentType;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        String petName,
        String vetName,
        AppointmentType type,
        AppointmentStatus status,
        LocalDateTime scheduledAt,
        String notes
) {
    public static AppointmentResponse from(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPet().getName(),
                appointment.getVet().getUser().getName(),
                appointment.getType(),
                appointment.getStatus(),
                appointment.getScheduledAt(),
                appointment.getNotes()
        );
    }
}
