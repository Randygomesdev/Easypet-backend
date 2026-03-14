package br.com.easypet.repository;
import br.com.easypet.domain.entity.Vet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {

    Page<Vet> findByactiveTrue(Pageable pageable);

    Optional<Vet> findByCrmv(String crmv);
    Optional<Vet> findByUserEmail(String email);

    boolean existsByCrmv(String crmv);
}
