package br.com.easypet.repository;

import br.com.easypet.domain.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPetId(Long petId);
    Page<Appointment> findByVetId(Long vetId, Pageable pageable);
    Page<Appointment> findByPetOwnerId(Long ownerId, Pageable pageable);

    Optional<Appointment> findByPetIdAndScheduledAt(Long petId, LocalDateTime scheduledAt);
    Optional<Appointment> findByIdAndPetOwnerId(Long id, Long ownerId);

    boolean existsByVetIdAndScheduledAt(Long vetId, LocalDateTime scheduledAt);
}
