package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.IndirizzoDTO;
import BW5.epicEnergy.entity.Comune;
import BW5.epicEnergy.entity.Indirizzo;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.ComuneRepository;
import BW5.epicEnergy.repositories.IndirizzoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor

public class IndirizzoService {
    private final ComuneRepository comuneRepository;
    private final IndirizzoRepository indirizzoRepository;

    public Comune findByComuneId(Long id) {
        Comune c = comuneRepository.findComuneById(id);
        if (c == null) {
            throw new NotFoundException("Comune con ID " + id + " non trovato.");
        }
        return c;
    }

    public Indirizzo save(IndirizzoDTO indirizzoDTO) {
        Comune found = findByComuneId(indirizzoDTO.comune());

        if (indirizzoRepository.existsIndirizzoByViaAndCapAndCivicoAndLocalitaAndComune(indirizzoDTO.via(), indirizzoDTO.cap(), indirizzoDTO.civico(), indirizzoDTO.localita(), found)) {
            throw new BadRequestException("Indirizzo già esistente nel DB");
        }

        Indirizzo nuovoIndirizzo = new Indirizzo(
                indirizzoDTO.via(),
                indirizzoDTO.civico(),
                indirizzoDTO.localita(),
                indirizzoDTO.cap(),
                found
        );

        return indirizzoRepository.save(nuovoIndirizzo);
    }

    public Indirizzo findByViaAndCivicoAndLocalitaAndCapAndComune_Id(String via, String civico, String localita, String cap, Long comuneId) {
        return this.indirizzoRepository.findByViaAndCivicoAndLocalitaAndCapAndComune_Id(via, civico, localita, cap, comuneId).orElseThrow(() -> new NotFoundException("address"));
    }

    public void deleteIndirizzo(UUID id) {
        // if exists
        if (!indirizzoRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossibile eliminare: Indirizzo non trovato con id: " + id);
        }
        indirizzoRepository.deleteById(id);
    }

    public Indirizzo updateIndirizzo(UUID id, Indirizzo details) {
        Indirizzo existing = indirizzoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Indirizzo non trovato con id: " + id));

        existing.setVia(details.getVia());
        existing.setCivico(details.getCivico());
        existing.setCap(details.getCap());
        existing.setLocalita(details.getLocalita());

        if (details.getComune() != null) {
            existing.setComune(details.getComune());
        }

        return indirizzoRepository.save(existing);
    }
}
