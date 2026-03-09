package br.com.easypet.dto.request;

import br.com.easypet.domain.enums.Gender;
import br.com.easypet.domain.enums.Species;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PetRequest(

        @NotBlank (message = "Nome é obrigatório")
        String name,

        @NotNull(message = "Espécie é obrigatória")
        Species species,

        @NotBlank(message = "Raça é obrigatória")
        String breed,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve ser no pasado")
        LocalDate birthDate,

        String microchipNumber,

        @NotNull(message = "Sexo é obrigatório")
        Gender gender
) {}
