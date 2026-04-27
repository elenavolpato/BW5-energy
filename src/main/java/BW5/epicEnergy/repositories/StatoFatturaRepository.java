package BW5.epicEnergy.repositories;

import BW5.epicEnergy.entity.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatoFatturaRepository extends JpaRepository<StatoFattura, UUID> {
    boolean existsByTipo(String tipo);

    Optional<StatoFattura> findByTipo(String tipo);

}
