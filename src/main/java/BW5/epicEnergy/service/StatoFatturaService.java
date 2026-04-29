package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.StatoFatturaDTO;
import BW5.epicEnergy.entity.StatoFattura;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.StatoFatturaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class StatoFatturaService {
    private final StatoFatturaRepository statoFatturaRepository;

    public UUID save(StatoFatturaDTO body) {
        if (statoFatturaRepository.existsByTipo(body.tipo()))
            throw new BadRequestException("Tipo di stato già presente nel database");
        StatoFattura nuovoStatoFattura = new StatoFattura(body.tipo().toUpperCase().trim());
        StatoFattura statoFatturaSalvato = this.statoFatturaRepository.save(nuovoStatoFattura);
        log.info("Stato fattura con id " + statoFatturaSalvato.getId() + " salvato con successo!");
        return statoFatturaSalvato.getId();
    }

    public StatoFattura findByTipo(String tipo) {
        return this.statoFatturaRepository.findByTipo(tipo.toUpperCase().trim()).orElseThrow(() -> new NotFoundException("invoice status"));
    }
}
