package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.FatturaDTO;
import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.entity.Fattura;
import BW5.epicEnergy.entity.StatoFattura;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.FattureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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

    //operazioni CRUD
    public Fattura findById(UUID id) {
        return fattureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vedi che hai preso la fattura di qualcun'altro..."));
    }

    public List<Fattura> findAll() {
        return fattureRepository.findAll();
    }

    //aggiungo anche la modifica perche' sta scritto nella consegna, poi la eliminiamo se non serve
    public Fattura updateFattura(UUID id, FatturaDTO body) {
        Fattura fattura = findById(id);
        StatoFattura nuovoStato = this.statoFatturaService.findByTipo("CARICATA");
        fattura.setImporto(body.importo());
        fattura.setStato(nuovoStato);
        return fattureRepository.save(fattura);
    }

    public void deleteById(UUID id) {
        Fattura fattura =  findById(id);
        fattureRepository.delete(fattura);
    }
}
