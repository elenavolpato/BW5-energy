package BW5.epicEnergy.repositories;

import BW5.epicEnergy.entity.Provincia;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinciaRepository  extends JpaRepository<Provincia, Long> {
    Optional<Provincia> findBySigla(String sigla);

    Optional<Provincia> findByNomeIgnoreCase(String nome);

}
