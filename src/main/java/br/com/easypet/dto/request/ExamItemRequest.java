package br.com.easypet.dto.request;

import jakarta.validation.constraints.NotNull;

public record ExamItemRequest(
    @NotNull(message = "O nome do exame é obrigatório")
    String examName,
    String reason
) {

}
