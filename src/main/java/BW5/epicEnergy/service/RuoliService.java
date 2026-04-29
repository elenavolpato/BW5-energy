package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.RuoliDTO;
import BW5.epicEnergy.entity.Ruoli;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.RuoliRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Ruoli> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 10;
        if (size < 0) size = 1;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.ruoliRepository.findAll(pageable);
    }

    public void delete(String ruolo) {
        String toDelete = ruolo.trim().toUpperCase();
        this.ruoliRepository.delete(this.findByRuolo(toDelete));
    }

}
