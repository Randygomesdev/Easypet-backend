package br.com.easypet.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RegisterVetRequest(

        @NotNull(message = "Dados de acesso são obrigatórios")
        @Valid
        RegisterRequest credentials,

        @NotNull(message = "Dados profissionais são obrigatórios")
        @Valid
        VetRequest professional
) {}
