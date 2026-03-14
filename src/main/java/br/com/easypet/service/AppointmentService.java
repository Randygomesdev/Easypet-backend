package br.com.easypet.service;

import br.com.easypet.domain.entity.Appointment;
import br.com.easypet.domain.entity.Pet;
import br.com.easypet.domain.entity.User;
import br.com.easypet.domain.entity.Vet;
import br.com.easypet.domain.enums.AppointmentStatus;
import br.com.easypet.dto.request.AppointmentRequest;
import br.com.easypet.dto.response.AppointmentResponse;
import br.com.easypet.exception.BusinessException;
import br.com.easypet.exception.ResourceNotFoundException;
import br.com.easypet.repository.AppointmentRepository;
import br.com.easypet.repository.PetRepository;
import br.com.easypet.repository.UserRepository;
import br.com.easypet.repository.VetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final UserRepository userRepository;

    @Transactional
    public AppointmentResponse create(AppointmentRequest request) {
        User owner = getAuthenticatedUser();

        Pet pet = petRepository.findByIdAndOwner(request.petId(), owner)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));

        Vet vet = vetRepository.findById(request.vetId())
                .orElseThrow(()-> new ResourceNotFoundException("Veterinário não encontrado"));

        if (!vet.getActive()){
            throw new BusinessException("Veterinário não está ativo");
        }

        if (appointmentRepository.existsByVetIdAndScheduledAt(request.vetId(), request.scheduledAt())) {
            throw new BusinessException("Veterinário já possui agendamento neste horário");
        }

        Appointment appointment = Appointment.builder()
                .pet(pet)
                .vet(vet)
                .type(request.type())
                .status(AppointmentStatus.SCHEDULED)
                .scheduledAt(request.scheduledAt())
                .notes(request.notes())
                .build();

        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    @Transactional
    public Page<AppointmentResponse> findAll(Pageable pageable) {
        User owner = getAuthenticatedUser();
        return appointmentRepository.findByPetOwnerId(owner.getId(), pageable)
                .map(AppointmentResponse::from);
    }

    @Transactional
    public AppointmentResponse findById(Long id) {
        User owner = getAuthenticatedUser();
        return appointmentRepository.findByIdAndPetOwnerId(id, owner.getId())
                .map(AppointmentResponse::from)
                .orElseThrow(()-> new ResourceNotFoundException("Agendamento não encontrado"));
    }

    @Transactional
    public AppointmentResponse cancel(Long id) {
        User owner = getAuthenticatedUser();

        Appointment appointment = appointmentRepository.findByIdAndPetOwnerId(id, owner.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado"));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new BusinessException("Não é possível cancelar um agendamento já concluído");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new BusinessException("Agendamento já está cancelado");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    @Transactional
    public Page<AppointmentResponse> findByVet(Pageable pageable) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Vet vet = vetRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado"));

        return appointmentRepository.findByVetId(vet.getId(), pageable)
                .map(AppointmentResponse::from);
    }

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
