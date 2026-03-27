package br.com.easypet.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record VaccineApplicationRequest(
    @NotNull(message = "O ID do pet é obrigatório")
    Long petId,

    @NotNull(message = "O nome da vacina é obrigatório")
    String name,

    @NotNull(message = "O laboratório é obrigatório")
    String laboratory,

    @NotNull(message = "O lote é obrigatório")
    String lote,

    @NotNull(message = "A data da dose é obrigatória")
    @PastOrPresent(message = "A data da dose não pode ser no futuro")
    LocalDate doseDate,

    @FutureOrPresent(message = "A data da próxima dose deve ser maior ou igual a data atual")
    LocalDate nextDoseDate,

    String notes

) {
    
}
