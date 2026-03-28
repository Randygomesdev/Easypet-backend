package br.com.easypet.dto.request;

import jakarta.validation.constraints.NotNull;

public record PrescriptionItemRequest(
    @NotNull(message = "O nome do medicamento é obrigatório")
    String medicineName,
    @NotNull(message = "A dosagem é obrigatória")
    String dosage,
    @NotNull(message = "A duração/frequência é obrigatória")
    String duration
) {

}
