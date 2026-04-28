package BW5.epicEnergy.repositories;

import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.entity.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientiRepository extends JpaRepository<Cliente, UUID> {
    boolean existsByPartitaIva(String partitaIva);

    boolean existsBySedeLegaleOrSedeOperativa(Indirizzo sedeLegale, Indirizzo sedeOperativa);
}
