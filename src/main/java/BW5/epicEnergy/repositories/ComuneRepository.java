package BW5.epicEnergy.repositories;

import BW5.epicEnergy.entity.Comune;
import BW5.epicEnergy.entity.Indirizzo;
import BW5.epicEnergy.entity.Provincia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {
    List<Comune> findByNome(String nome);

    Comune findComuneById(Long id);

    List<Comune> findByProvincia_SiglaIgnoreCase(String sigla);}
