package BW5.epicEnergy.repositories;

import BW5.epicEnergy.entity.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {
    List<Comune> findByNome(String nome);
}
