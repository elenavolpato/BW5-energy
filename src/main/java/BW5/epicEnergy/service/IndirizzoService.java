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
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IndirizzoService {
    private final ComuneRepository comuneRepository;
    //private  final ProvinciaRepository provinciaRepository;
    private  final IndirizzoRepository indirizzoRepository;

    public Comune findById(Long id){
        System.out.println("----------- " + id);
        return comuneRepository.findById(id).orElseThrow(() -> new NotFoundException("Indirizzo Id"));
    }

    public Indirizzo save(IndirizzoDTO indirizzoDTO ){
        System.out.println("-------------->>>> " + indirizzoDTO.comune());
        Comune found = findById(indirizzoDTO.comune());

        if(indirizzoRepository.existsIndirizzoByViaAndCapAndCivicoAndLocalitaAndComune(indirizzoDTO.via(), indirizzoDTO.cap(), indirizzoDTO.civico(), indirizzoDTO.localita(), indirizzoDTO.comune())){
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
