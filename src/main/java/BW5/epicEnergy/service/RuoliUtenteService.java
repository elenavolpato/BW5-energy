package BW5.epicEnergy.service;

import BW5.epicEnergy.repositories.RuoliUtenteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RuoliUtenteService {
    private final RuoliUtenteRepository ruoliUtenteRepository;
    private final RuoliService ruoliService;
    /*private final UtenteService utenteService;*/

    /*public RuoliUtente save(AssegnazioneRuoloUtenteDTO body) {
        Ruoli ruoloDalDB = ruoliService.findByRuolo(body.ruolo().toUpperCase().trim());
        Utente utenteDalDB = utenteService.findById(body.idUtente());
        if (ruoliUtenteRepository.existsByRuolo_IdAndUtente_Id(ruoloDalDB.getId(), utenteDalDB.getId()))
            throw new BadRequestException("Ruolo " + ruoloDalDB.getRuolo() + " già assegnato all'utente con id " + utenteDalDB.getId());
        RuoliUtente nuovaAssegnazioneRuolo = new RuoliUtente(ruoloDalDB, utenteDalDB);
        RuoliUtente assegnazioneRuoloSalvata = this.ruoliUtenteRepository.save(nuovaAssegnazioneRuolo);
        log.info("Ruolo di '" + ruoloDalDB.getRuolo() + "' assegnato con successo all'utente con id " + utenteDalDB.getId());
        return assegnazioneRuoloSalvata;
    }*/;
}
