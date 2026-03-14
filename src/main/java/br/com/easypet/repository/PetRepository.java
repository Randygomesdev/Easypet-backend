package br.com.easypet.repository;

import br.com.easypet.domain.entity.Pet;
import br.com.easypet.domain.entity.User;
import br.com.easypet.domain.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findByOwner(User owner, Pageable pageable);

    Optional<Pet> findByIdAndOwner(Long id, User owner);

    boolean existsByMicrochipNumber(String microchipNumber);
}
