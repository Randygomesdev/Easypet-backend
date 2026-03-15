package br.com.easypet.service;

import br.com.easypet.domain.entity.Vet;
import br.com.easypet.dto.request.VetRequest;
import br.com.easypet.dto.response.VetResponse;
import br.com.easypet.exception.BusinessException;
import br.com.easypet.exception.ResourceNotFoundException;
import br.com.easypet.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VetService {

    private final VetRepository vetRepository;

    @Cacheable(value = "vets", key = "'all'")
    @Transactional
    public Page<VetResponse> findAll(Pageable pageable) {
        return vetRepository.findByactiveTrue(pageable)
                .map(VetResponse::from);
    }

    @Cacheable(value = "vets", key = "#id")
    @Transactional
    public VetResponse findById(Long id) {
        return vetRepository.findById(id)
                .map(VetResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado"));
    }

    @CacheEvict(value = "vets", allEntries = true)
    @Transactional
    public VetResponse update(Long id, VetRequest request) {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado"));

        if (!vet.getCrmv().equals(request.crmv()) &&
                vetRepository.existsByCrmv(request.crmv())) {
            throw new BusinessException("CRMV já cadastrado");
        }

        vet.setCrmv(request.crmv());
        vet.setSpecialty(request.specialty());
        vet.setPhone(request.phone());
        vet.setActive(request.active());

        return VetResponse.from(vetRepository.save(vet));
    }

    @CacheEvict(value = "vets", allEntries = true)
    @Transactional
    public void deactivate(Long id) {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado"));
        vet.setActive(false);
        vetRepository.save(vet);
    }
}