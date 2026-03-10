package br.com.easypet.repository;

import br.com.easypet.domain.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPetId(Long petId);
    List<Appointment> findByVetId(Long vetId);
    List<Appointment> findByPetOwnerId(Long ownerId);

    Optional<Appointment> findByPetIdAndScheduledAt(Long petId, LocalDateTime scheduledAt);
    Optional<Appointment> findByIdAndPetOwnerId(Long id, Long ownerId);

    boolean existsByVetIdAndScheduledAt(Long vetId, LocalDateTime scheduledAt);
}
