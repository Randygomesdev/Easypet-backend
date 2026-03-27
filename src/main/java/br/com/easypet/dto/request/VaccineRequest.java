package br.com.easypet.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record VaccineRequest(

        @NotNull(message = "Pet é obrigatório")
        Long petId,

        @NotNull(message = "Nome é obrigatório")
        String name,

        @NotNull(message = "Laboratório é obrigatório")
        String laboratory,

        @NotNull(message = "Lote é obrigatório")
        String lote,

        @NotNull(message = "Data de aplicação é obrigatória")
        @PastOrPresent(message = "Data da aplicação não pode ser no futuro")
        LocalDate doseDate,

        @Future(message = "Próxima dose deve ser no futuro")
        LocalDate nextDoseDate,
        String vetName,
        String notes
) {
}
