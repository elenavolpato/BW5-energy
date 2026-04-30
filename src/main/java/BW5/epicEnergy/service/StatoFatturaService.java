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
   /* private final FattureService fattureService;
    private final FattureRepository fattureRepository;*/

    public UUID save(StatoFatturaDTO body) {
        if (statoFatturaRepository.existsByTipo(body.tipo().toUpperCase().trim()))
            throw new BadRequestException("Tipo di stato già presente nel database");
        StatoFattura nuovoStatoFattura = new StatoFattura(body.tipo().toUpperCase().trim());
        StatoFattura statoFatturaSalvato = this.statoFatturaRepository.save(nuovoStatoFattura);
        log.info("Stato fattura con id " + statoFatturaSalvato.getId() + " salvato con successo!");
        return statoFatturaSalvato.getId();
    }

    public StatoFattura findByTipo(String tipo) {
        return this.statoFatturaRepository.findByTipo(tipo.toUpperCase().trim()).orElseThrow(() -> new NotFoundException("invoice status"));
    }

    /*public StatoFattura finbByTipoAndUpdate(String tipo, StatoFatturaDTO body) {
        StatoFattura toUpdate = this.findByTipo(tipo);
        toUpdate.setTipo(body.tipo());
        StatoFattura updated = this.statoFatturaRepository.save(toUpdate);
        return updated;
    }*/

   /* public void delete(String tipo) {

        String noState = "nessuno stato";
        StatoFattura statoDefault;

        if (tipo.equals(noState)) {
            throw new IllegalArgumentException("Non puoi cancellare lo stato di default");
        }
        // se cancello uno stato che è in utilizzo in una fattura -> assegno uno stato di default.
        //1 cerco fatture associate allo stato che voglio cancellare
        StatoFattura toDelete = this.findByTipo(tipo);
        List<Fattura> fattureConStato = this.fattureService.findByStato(tipo);
        //2 modifico lo stato di quelle fatture
        if (this.statoFatturaRepository.existsByTipo(noState)) {
            statoDefault = this.findByTipo(noState);
        } else {
            statoDefault = statoFatturaRepository.save(new StatoFattura(noState));
        }
        for (Fattura f : fattureConStato) {
            f.setStato(statoDefault);

        }
        // Salvo le fatture con il nuovo stato
        this.fattureRepository.saveAll(fattureConStato);
        // cancello lo stato vecchio
        this.statoFatturaRepository.delete(toDelete);
    }*/

}

































