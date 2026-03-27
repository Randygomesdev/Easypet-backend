package br.com.easypet.dto.response;

import br.com.easypet.domain.entity.Vaccine;
import br.com.easypet.domain.enums.VaccineStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record VaccineResponse(
        Long id,
        String name,
        String laboratory,
        String lote,
        LocalDate doseDate,
        LocalDate nextDoseDate,
        String vetName,
        String notes,
        VaccineStatus status
) {
    public static VaccineResponse from(Vaccine vaccine) {
        return new VaccineResponse(
                vaccine.getId(),
                vaccine.getName(),
                vaccine.getLaboratory(),
                vaccine.getLote(),
                vaccine.getDoseDate(),
                vaccine.getNextDoseDate(),
                vaccine.getVetName(),
                vaccine.getNotes(),
                vaccine.getStatus()
        );
    }
}
