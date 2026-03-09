package br.com.easypet.repository;

import br.com.easypet.domain.entity.Pet;
import br.com.easypet.domain.entity.User;
import br.com.easypet.domain.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByOwner(User owner);

    Optional<Pet> findByIdAndOwner(Long id, User owner);

    boolean existsByMicrochipNumber(String microchipNumber);
}
