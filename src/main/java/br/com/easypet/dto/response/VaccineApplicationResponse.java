package br.com.easypet.dto.response;

import br.com.easypet.domain.entity.Vaccine;

import java.time.LocalDate;

public record VaccineApplicationResponse(
        Long id,
        String name,
        String laboratory,
        String lote,
        LocalDate doseDate,
        LocalDate nextDoseDate,
        String vetName,
        String notes
) {

    public static VaccineApplicationResponse from(Vaccine vaccine) {
        return new VaccineApplicationResponse(
                vaccine.getId(),
                vaccine.getName(),
                vaccine.getLaboratory(),
                vaccine.getLote(),
                vaccine.getDoseDate(),
                vaccine.getNextDoseDate(),
                vaccine.getVetName(),
                vaccine.getNotes()
        );
    }
}
