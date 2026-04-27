package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.FatturaDTO;
import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.entity.Fattura;
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

    public UUID save(FatturaDTO body) {
        Cliente found = this.clientiService.findById(body.clienteId());
        Fattura nuovaFattura = new Fattura(body.importo(), found);
        Fattura fatturaSalvata = this.fattureRepository.save(nuovaFattura);
        log.info("Fattura con id " + fatturaSalvata.getId() + " salvata con successo!");
        return fatturaSalvata.getId();
    }
}
