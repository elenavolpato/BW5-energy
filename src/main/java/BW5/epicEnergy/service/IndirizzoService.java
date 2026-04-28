package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.IndirizzoDTO;
import BW5.epicEnergy.entity.Comune;
import BW5.epicEnergy.entity.Indirizzo;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.ComuneRepository;
import BW5.epicEnergy.repositories.IndirizzoRepository;
//import BW5.epicEnergy.repositories.ProvinciaRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class IndirizzoService {
    private final ComuneRepository comuneRepository;
    //private  final ProvinciaRepository provinciaRepository;
    private  final IndirizzoRepository indirizzoRepository;

    public Comune findByComuneId(Long id) {
        Comune c = comuneRepository.findComuneById(id);
        if (c == null) {
            throw new NotFoundException("Comune con ID " + id + " non trovato.");
        }
        return c;
    }

    public Indirizzo save(IndirizzoDTO indirizzoDTO ){
        Comune found = findByComuneId(indirizzoDTO.comune());

        if(indirizzoRepository.existsIndirizzoByViaAndCapAndCivicoAndLocalitaAndComune(indirizzoDTO.via(), indirizzoDTO.cap(), indirizzoDTO.civico(), indirizzoDTO.localita(), found)){
            throw new BadRequestException("Indirizzo già esistente nell DB");
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
}
