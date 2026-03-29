package br.com.easypet.repository;

import br.com.easypet.domain.entity.Pet;
import br.com.easypet.domain.entity.Vaccine;
import br.com.easypet.domain.enums.VaccineStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    List<Vaccine> findByPetAndStatus(Pet pet, VaccineStatus status);
    @Query("SELECT v FROM Vaccine v JOIN FETCH v.pet p JOIN FETCH p.owner WHERE v.nextDoseDate BETWEEN :start AND :end")
    List<Vaccine> findByNextDoseDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    Page<Vaccine> findByPetId (Long petId, Pageable pageable);

    Optional<Vaccine> findByIdAndPetOwnerId(Long id, Long ownerId);

}
