package BW5.epicEnergy.repositories;

import BW5.epicEnergy.entity.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FattureRepository extends JpaRepository<Fattura, UUID> {
}
