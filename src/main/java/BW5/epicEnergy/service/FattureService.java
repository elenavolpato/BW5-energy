package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.FatturaDTO;
import BW5.epicEnergy.DTO.NuovoStatoFatturaDTO;
import BW5.epicEnergy.entity.Cliente;
import BW5.epicEnergy.entity.Fattura;
import BW5.epicEnergy.entity.StatoFattura;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.FattureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        StatoFattura caricata = this.statoFatturaService.findByTipo("CREATA");
        Fattura nuovaFattura = new Fattura(body.importo(), found, caricata);
        Fattura fatturaSalvata = this.fattureRepository.save(nuovaFattura);
        log.info("Fattura con id " + fatturaSalvata.getId() + " salvata con successo!");
        return fatturaSalvata.getId();
    }

    public Fattura findById(UUID fatturaID) {
        return this.fattureRepository.findById(fatturaID).orElseThrow(() -> new NotFoundException("fattura"));
    }

    public List<Fattura> findByStato(String stato) {
        List<Fattura> res = new ArrayList<>();
        try {
            res = this.fattureRepository.findByStato(stato);
        } catch (NotFoundException ex) {
            throw new NotFoundException("lista di fatture");
        }
        return res;
    }


    public Page<Fattura> findAll(Specification<Fattura> specification, int page, int size, String sortBy, String order) {
        if (page < 0) page = 0;
        if (size < 0 || size > 100) size = 10;

        String criterioOrdine = switch (sortBy) {
            case "data" -> "data";
            case "importo" -> "importo";
            case "numero" -> "numero";
            default -> throw new BadRequestException("Criterio di ordinamento non valido");
        };

        Pageable pageable = switch (order) {
            case "asc" -> PageRequest.of(page, size, Sort.by(criterioOrdine));
            case "disc" -> PageRequest.of(page, size, Sort.by(criterioOrdine).reverse());
            default -> throw new BadRequestException("Criterio di ordinamento non valido");
        };

        return this.fattureRepository.findAll(specification, pageable);
    }

    public void delete(UUID fatturaId) {
        this.fattureRepository.delete(this.findById(fatturaId));
    }


    public Fattura findByIdAndUpdate(UUID fatturaId, NuovoStatoFatturaDTO body) {

        Fattura found = this.findById(fatturaId);

        found.setStato(body.stato());

        Fattura updateFattura = this.fattureRepository.save(found);

        return updateFattura;
    }


}
