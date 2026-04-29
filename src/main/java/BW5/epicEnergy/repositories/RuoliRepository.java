package BW5.epicEnergy.repositories;

import BW5.epicEnergy.entity.Ruoli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuoliRepository extends JpaRepository<Ruoli, UUID> {
    boolean existsByRuolo(String ruolo);

    Optional<Ruoli> findByRuolo(String ruolo);
}
