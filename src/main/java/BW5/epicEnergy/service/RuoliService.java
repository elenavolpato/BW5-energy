package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.RuoliDTO;
import BW5.epicEnergy.entity.Ruoli;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.RuoliRepository;
import org.springframework.stereotype.Service;

@Service
public class RuoliService {

    private final RuoliRepository ruoliRepository;

    public RuoliService(RuoliRepository ruoliRepository) {
        this.ruoliRepository = ruoliRepository;
    }

    public Ruoli create(RuoliDTO body) {
        String ruolo = body.ruolo().trim().toUpperCase();
        if (this.ruoliRepository.existsByRuolo(ruolo))
            throw new BadRequestException("Questo nuovo ruolo che stai creando esiste già");

        return this.ruoliRepository.save(new Ruoli(ruolo));
    }

    public Ruoli findByRuolo(String ruolo) {
        String toFind = ruolo.trim().toUpperCase();
        return this.ruoliRepository.findByRuolo(toFind).orElseThrow(() -> new NotFoundException("Ruolo"));
    }

    public void delete(String ruolo) {
        String toDelete = ruolo.trim().toUpperCase();
        this.ruoliRepository.delete(this.findByRuolo(toDelete));
    }

}
