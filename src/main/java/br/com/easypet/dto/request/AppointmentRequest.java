package br.com.easypet.dto.request;

import br.com.easypet.domain.enums.AppointmentStatus;
import br.com.easypet.domain.enums.AppointmentType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequest(

        @NotNull(message = "Pet é obrigatório")
        Long petId,

        @NotNull(message = "Veterinário é obrigatório")
        Long vetId,

        @NotNull(message = "Tipo é obrigatório")
        AppointmentType type,

        @NotNull(message = "Data e hora são obrigatórios")
        @Future(message = "Data deve ser no futuro")
        LocalDateTime scheduledAt,

        String notes
) {}
