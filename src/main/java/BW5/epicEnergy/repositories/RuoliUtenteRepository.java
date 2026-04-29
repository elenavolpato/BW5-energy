package BW5.epicEnergy.repositories;

import BW5.epicEnergy.entity.Ruoli;
import BW5.epicEnergy.entity.RuoliUtente;
import BW5.epicEnergy.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RuoliUtenteRepository extends JpaRepository<RuoliUtente, UUID> {
    boolean existsByRuolo_IdAndUtente_Id(UUID idRuolo, UUID idUtente);

    Optional<RuoliUtente> findByRuoloAndUtente(Ruoli ruolo, Utente utente);
}
