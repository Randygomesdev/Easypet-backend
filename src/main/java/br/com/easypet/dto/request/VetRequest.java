package br.com.easypet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VetRequest(

        @NotBlank(message = "CRMV é obrigatório")
        String crmv,

        @NotBlank(message = "Especialidade é obrigatória")
        String specialty,

        @NotBlank(message = "Telefone é obrigatório")
        String phone,

        @NotNull(message = "Status é obrigatório")
        Boolean active
) {}