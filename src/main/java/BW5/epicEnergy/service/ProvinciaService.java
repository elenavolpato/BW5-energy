package BW5.epicEnergy.service;

import BW5.epicEnergy.entity.Provincia;
import BW5.epicEnergy.repositories.ProvinciaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProvinciaService {
    private final ProvinciaRepository provinciaRepository;

    public List<Provincia> findAllProvince() {
        return provinciaRepository.findAll();
    }
}
