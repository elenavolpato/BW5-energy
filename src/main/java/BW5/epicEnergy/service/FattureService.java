package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.FatturaDTO;
import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.entity.Fattura;
import BW5.epicEnergy.entity.StatoFattura;
import BW5.epicEnergy.repositories.FattureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class FattureService {
    private final FattureRepository fattureRepository;
    private final ClientiService clientiService;
    private final StatoFatturaService statoFatturaService;

    public UUID save(FatturaDTO body) {
        Cliente found = this.clientiService.findById(body.clienteId());
        StatoFattura caricata = this.statoFatturaService.findByTipo("CARICATA");
        Fattura nuovaFattura = new Fattura(body.importo(), found, caricata);
        Fattura fatturaSalvata = this.fattureRepository.save(nuovaFattura);
        log.info("Fattura con id " + fatturaSalvata.getId() + " salvata con successo!");
        return fatturaSalvata.getId();
    }
}
