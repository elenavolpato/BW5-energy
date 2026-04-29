package BW5.epicEnergy.service;

import BW5.epicEnergy.DTO.AssegnazioneRuoloUtenteDTO;
import BW5.epicEnergy.DTO.EmailDTO;
import BW5.epicEnergy.DTO.InvioEmailDTO;
import BW5.epicEnergy.DTO.UtenteDTO;
import BW5.epicEnergy.entity.Ruoli;
import BW5.epicEnergy.entity.RuoliUtente;
import BW5.epicEnergy.entity.Utente;
import BW5.epicEnergy.exception.BadRequestException;
import BW5.epicEnergy.exception.EmailAlreadyExistsException;
import BW5.epicEnergy.exception.NotFoundException;
import BW5.epicEnergy.repositories.RuoliUtenteRepository;
import BW5.epicEnergy.repositories.UtenteRepository;
import BW5.epicEnergy.tools.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder bcrypt;
    private final EmailSender emailSender;
    private final RuoliUtenteService ruoliUtenteService;
    private final RuoliService ruoliService;
    private final RuoliUtenteRepository ruoliUtenteRepository;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder bcrypt, EmailSender emailSender, RuoliUtenteService ruoliUtenteService, RuoliService ruoliService, RuoliUtenteRepository ruoliUtenteRepository) {
        this.utenteRepository = utenteRepository;
        this.bcrypt = bcrypt;
        this.emailSender = emailSender;
        this.ruoliUtenteService = ruoliUtenteService;
        this.ruoliService = ruoliService;
        this.ruoliUtenteRepository = ruoliUtenteRepository;
    }

    public RuoliUtente assegnaRuoloAUtente(AssegnazioneRuoloUtenteDTO body) {
        Ruoli ruoloDalDB = ruoliService.findByRuolo(body.ruolo().toUpperCase().trim());
        Utente utenteDalDB = findById(body.idUtente());
        if (ruoliUtenteRepository.existsByRuolo_IdAndUtente_Id(ruoloDalDB.getId(), utenteDalDB.getId()))
            throw new BadRequestException("Ruolo " + ruoloDalDB.getRuolo() + " già assegnato all'utente con id " + utenteDalDB.getId());
        RuoliUtente nuovaAssegnazioneRuolo = new RuoliUtente(ruoloDalDB, utenteDalDB);
        RuoliUtente assegnazioneRuoloSalvata = this.ruoliUtenteRepository.save(nuovaAssegnazioneRuolo);
        log.info("Ruolo di '" + ruoloDalDB.getRuolo() + "' assegnato con successo all'utente con id " + utenteDalDB.getId());
        return assegnazioneRuoloSalvata;
    }


    public Utente save(UtenteDTO body) {

        if (this.utenteRepository.existsByEmail((body.email())))
            throw new EmailAlreadyExistsException("L'email" + body.email() + " con cui stai provando a registrarti è già associata ad un altro utente.");

        Utente newU = this.utenteRepository.save(new Utente(body.username(), body.email(), bcrypt.encode(body.password()), body.nome(), body.cognome()));

        this.assegnaRuoloAUtente(new AssegnazioneRuoloUtenteDTO("UTENTE", newU.getId()));
        // qia assegno di default il ruolo di UTENTE
        /*newU.addRuolo("utente");*/

        return newU;
    }

    public Utente findById(UUID utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException("utente"));
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente"));
    }

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 10;
        if (size < 0) size = 1;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteRepository.findAll(pageable);
    }

    public Utente update(UUID utenteId, UtenteDTO body) {
        Utente toUpdate = this.findById(utenteId);

        if (!toUpdate.getEmail().equals(body.email()))
            throw new EmailAlreadyExistsException("L'email" + body.email() + " con cui stai provando a registrarti è già associata ad un altro utente.");

        toUpdate.setUsername(body.username());
        toUpdate.setEmail(body.email());
        toUpdate.setPassword(bcrypt.encode(body.password()));
        toUpdate.setNome(body.nome());
        toUpdate.setCognome(body.cognome());

        return toUpdate;
    }

    public void delete(UUID utenteId) {
        this.utenteRepository.delete(this.findById(utenteId));
    }

    public InvioEmailDTO inviaEmail(Utente currentAuthenticatedUser, EmailDTO body) {
        return this.emailSender.sendEmail(currentAuthenticatedUser, body);
    }

}
