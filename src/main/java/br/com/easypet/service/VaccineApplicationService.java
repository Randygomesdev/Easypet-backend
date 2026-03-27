package br.com.easypet.service;

import br.com.easypet.domain.entity.Pet;
import br.com.easypet.domain.entity.User;
import br.com.easypet.domain.entity.Vaccine;
import br.com.easypet.domain.enums.VaccineStatus;
import br.com.easypet.dto.request.VaccineApplicationRequest;
import br.com.easypet.dto.response.VaccineApplicationResponse;
import br.com.easypet.exception.ResourceNotFoundException;
import br.com.easypet.repository.PetRepository;
import br.com.easypet.repository.UserRepository;
import br.com.easypet.repository.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VaccineApplicationService {

    private final VaccineRepository vaccineRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Transactional
    public VaccineApplicationResponse create(VaccineApplicationRequest request) {
        User vet = getAuthenticatedUser();

        Pet pet = petRepository.findById(request.petId())
                .orElseThrow(()-> new ResourceNotFoundException("Pet não encontrado"));

        Vaccine vaccine = Vaccine.builder()
                .pet(pet)
                .name(request.name())
                .laboratory(request.laboratory())
                .lote(request.lote())
                .doseDate(request.doseDate())
                .nextDoseDate(request.nextDoseDate())
                .vetName(vet.getName())
                .notes(request.notes())
                .status(VaccineStatus.UPDATED)
                .build();

        return VaccineApplicationResponse.from(vaccineRepository.save(vaccine));
    }


    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado"));
    }

}
