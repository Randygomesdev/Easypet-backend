package br.com.easypet.repository;

import br.com.easypet.domain.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    boolean existsByAppointmentId(Long appointmentId);
}
