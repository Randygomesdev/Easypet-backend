package br.com.easypet.dto.response;

import br.com.easypet.domain.entity.Vet;

import java.io.Serializable;

public record VetResponse(
        Long id,
        String name,
        String crmv,
        String specialty,
        String phone,
        String email,
        Boolean active
) implements Serializable {
    public static VetResponse from(Vet vet) {
        return new VetResponse(
                vet.getId(),
                vet.getName(),
                vet.getCrmv(),
                vet.getSpecialty(),
                vet.getPhone(),
                vet.getUser().getEmail(),
                vet.getActive()
        );
    }
}