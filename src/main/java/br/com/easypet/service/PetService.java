package br.com.easypet.service;

import br.com.easypet.domain.entity.Pet;
import br.com.easypet.domain.entity.User;
import br.com.easypet.dto.request.PetRequest;
import br.com.easypet.dto.response.PetResponse;
import br.com.easypet.exception.BusinessException;
import br.com.easypet.exception.ResourceNotFoundException;
import br.com.easypet.repository.PetRepository;
import br.com.easypet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetResponse create(PetRequest request) {
        User owner = getAuthenticatedUser();

        if (request.microchipNumber() != null &&
                petRepository.existsByMicrochipNumber(request.microchipNumber())) {
            throw new BusinessException("Microchip já cadastrado");
        }

        Pet pet = Pet.builder()
                .name(request.name())
                .species(request.species())
                .breed(request.breed())
                .birthDate(request.birthDate())
                .microchipNumber(request.microchipNumber())
                .gender(request.gender())
                .owner(owner)
                .build();

        return PetResponse.from(petRepository.save(pet));
    }

    @Transactional
    public List<PetResponse> findAll() {
        User owner = getAuthenticatedUser();
        return petRepository.findByOwner(owner)
                .stream()
                .map(PetResponse::from)
                .toList();
    }

    @Transactional
    public PetResponse findById(Long id) {
        User owner = getAuthenticatedUser();
        return petRepository.findByIdAndOwner(id, owner)
                .map(PetResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));
    }

    @Transactional
    public PetResponse update(Long id, PetRequest request) {
        User owner = getAuthenticatedUser();

        Pet pet = petRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));

        if (request.microchipNumber() != null &&
                !request.microchipNumber().equals(pet.getMicrochipNumber()) &&
                petRepository.existsByMicrochipNumber(request.microchipNumber())) {
            throw new BusinessException("Microchip já cadastrado");
        }

        pet.setName(request.name());
        pet.setSpecies(request.species());
        pet.setBreed(request.breed());
        pet.setBirthDate(request.birthDate());
        pet.setMicrochipNumber(request.microchipNumber());
        pet.setGender(request.gender());

        return PetResponse.from(petRepository.save(pet));
    }

    public void delete(Long id) {
        User owner = getAuthenticatedUser();
        Pet pet = petRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));
        petRepository.delete(pet);
    }

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}