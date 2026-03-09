package br.com.easypet.dto.response;

import br.com.easypet.domain.entity.Pet;
import br.com.easypet.domain.enums.Gender;
import br.com.easypet.domain.enums.Species;

import java.time.LocalDate;

public record PetResponse(
        Long id,
        String name,
        Species species,
        String breed,
        LocalDate birthDate,
        String microchipNumber,
        Gender gender,
        String ownerName
) {
    public static PetResponse from(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getBirthDate(),
                pet.getMicrochipNumber(),
                pet.getGender(),
                pet.getOwner().getName()
        );
    }
}