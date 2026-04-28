package BW5.epicEnergy.repositories;

import BW5.epicEnergy.entity.RuoliUtente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RuoliUtenteRepository extends JpaRepository<UUID, RuoliUtente> {
}
