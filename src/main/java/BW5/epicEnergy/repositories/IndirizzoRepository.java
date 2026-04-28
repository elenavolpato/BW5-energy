package BW5.epicEnergy.repositories;

import BW5.epicEnergy.entity.Comune;
import BW5.epicEnergy.entity.Indirizzo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndirizzoRepository extends JpaRepository<Indirizzo, UUID> {
    boolean existsIndirizzoByViaAndCapAndCivicoAndLocalitaAndComune(String via, String cap, String civico, String localita, Comune comune);


}
