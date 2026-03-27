package br.com.easypet.service;

import br.com.easypet.domain.entity.Pet;
import br.com.easypet.domain.entity.User;
import br.com.easypet.domain.entity.Vaccine;
import br.com.easypet.domain.enums.VaccineStatus;
import br.com.easypet.dto.request.VaccineRequest;
import br.com.easypet.dto.response.VaccineResponse;
import br.com.easypet.exception.ResourceNotFoundException;
import br.com.easypet.repository.PetRepository;
import br.com.easypet.repository.UserRepository;
import br.com.easypet.repository.VaccineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Transactional
    public VaccineResponse create(VaccineRequest request){
        User owner = getAuthenticatedUser();

        Pet pet = petRepository.findByIdAndOwner(request.petId(), owner)
                .orElseThrow(()-> new ResourceNotFoundException("Pet não encontrado"));

        VaccineStatus status = calculateStatus(request.nextDoseDate());

        Vaccine vaccine = Vaccine.builder()
                .pet(pet)
                .name(request.name())
                .laboratory(request.laboratory())
                .lote(request.lote())
                .doseDate(request.doseDate())
                .nextDoseDate(request.nextDoseDate())
                .vetName(request.vetName())
                .notes(request.notes())
                .status(status)
                .build();

        return VaccineResponse.from(vaccineRepository.save(vaccine));
    }

    @Transactional
    public Page<VaccineResponse> findAll(Long petId, Pageable pageable){
        User owner = getAuthenticatedUser();

        Pet pet = petRepository.findByIdAndOwner(petId, owner)
                .orElseThrow(()-> new ResourceNotFoundException("Pet não encontrado"));

        return vaccineRepository.findByPetId(pet.getId(), pageable)
                .map(VaccineResponse::from);
    }

    @Transactional
    public VaccineResponse findById(Long id){
        User owner = getAuthenticatedUser();
        return vaccineRepository.findByIdAndPetOwnerId(id, owner.getId())
                .map(VaccineResponse::from)
                .orElseThrow(()-> new ResourceNotFoundException("Vacina não encontrada"));
    }

    @Transactional
    public VaccineResponse update(Long id, VaccineRequest request){
        User owner = getAuthenticatedUser();

        Vaccine vaccine = vaccineRepository.findByIdAndPetOwnerId(id, owner.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Vacina não encontrada"));

        Pet pet = petRepository.findByIdAndOwner(request.petId(), owner)
                .orElseThrow(()-> new ResourceNotFoundException("Pet não encontrado"));

        VaccineStatus status = calculateStatus(request.nextDoseDate());

        vaccine.setPet(pet);
        vaccine.setName(request.name());
        vaccine.setLaboratory(request.laboratory());
        vaccine.setLote(request.lote());
        vaccine.setDoseDate(request.doseDate());
        vaccine.setNextDoseDate(request.nextDoseDate());
        vaccine.setVetName(request.vetName());
        vaccine.setNotes(request.notes());
        vaccine.setStatus(status);

        return VaccineResponse.from(vaccineRepository.save(vaccine));
    }

    @Transactional
    public void delete(Long id){
        User owner = getAuthenticatedUser();
        Vaccine vaccine = vaccineRepository.findByIdAndPetOwnerId(id, owner.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Vacina não encontrada"));
        vaccineRepository.delete(vaccine);
    }

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
    }

    private VaccineStatus calculateStatus(LocalDate nextDoseDate) {
        if(nextDoseDate == null) {
            return VaccineStatus.UPDATED;
        }
        LocalDate today = LocalDate.now();
        if(nextDoseDate.isBefore(today)) {
            return VaccineStatus.OVERDUE;
        }
        if(nextDoseDate.isBefore(today.plusDays(30))) {
            return VaccineStatus.DUE_SOON;
        }
        return VaccineStatus.UPDATED;
    }
}
