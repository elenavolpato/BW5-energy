package BW5.epicEnergy.service;

import BW5.epicEnergy.entity.Comune;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.ComuneRepository;
import BW5.epicEnergy.repositories.ProvinciaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComuneService {
    private final ComuneRepository comuneRepository;

    public List<Comune> findAllComuniByProvincia(String sigla) {
        return comuneRepository.findByProvincia_SiglaIgnoreCase(sigla);
    }
}
